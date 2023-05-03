
package io.renren.commom;



import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.function.KeyRandomStrategy;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import io.renren.common.exception.RenException;
import io.renren.common.redis.RedisKeys;
import io.renren.common.redis.RedisService;
import io.renren.common.utils.*;
import io.renren.dto.MobileLoginDTO;
import io.renren.dto.SendSmsDTO;
import io.renren.dto.WeChatLoginDTO;
import io.renren.entity.UserEntity;
import io.renren.listener.OpenAIWebSocketEventSourceListener;
import io.renren.service.SysParamsService;
import io.renren.service.UserService;
import io.renren.vo.UserVo;
import io.renren.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

import com.unfbx.chatgpt.entity.chat.Message;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CommonService {

	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;
	@Autowired
	private AsyncService asyncService;
	private OpenAiStreamClient chatGPTStream;
	@Autowired
	private Gson gson;
    @Autowired
	private SysParamsService paramsService;
	@Autowired
	private SysParamsService sysParamsService;

	public void init() {
		Object o = redisService.get(RedisKeys.GPT_KEY);
		if(o == null){
			throw new RenException("暂未配置apikey");
		}
		List<String> list = JSON.parseArray(o.toString(), String.class);
		String chatGptUrl = sysParamsService.getValue("CHAT_GPT_URL");
		if(StrUtil.isBlank(chatGptUrl)){
			chatGptUrl = "https://api.openai.com/";
		}
		HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
		//!!!!!!测试或者发布到服务器千万不要配置Level == BODY!!!!
		//!!!!!!测试或者发布到服务器千万不要配置Level == BODY!!!!
		httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
		OkHttpClient okHttpClient = new OkHttpClient
				.Builder()
//                .proxy(proxy)
				.addInterceptor(httpLoggingInterceptor)
				.connectTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(60, TimeUnit.SECONDS)
				.readTimeout(600, TimeUnit.SECONDS)
				.build();
		chatGPTStream = OpenAiStreamClient
				.builder()
				.apiHost(chatGptUrl)
				.apiKey(list)
				//自定义key使用策略 默认随机策略
				.keyStrategy(new KeyRandomStrategy())
				.okHttpClient(okHttpClient)
				.build();
	}
	public void onMessage(String modeId,String msg, WebSocketServer webSocketServer) throws IOException {
		init();
		UserEntity userEntity = userService.selectById(webSocketServer.userId);
//        //接受参数
		OpenAIWebSocketEventSourceListener eventSourceListener = new OpenAIWebSocketEventSourceListener(webSocketServer.session,userEntity.getId(),asyncService,msg);
		String messageContext =  (String)redisService.get(webSocketServer.token);
		List<Message> messages = new ArrayList<>();
		if (StrUtil.isNotBlank(messageContext)) {
			messages = JSONUtil.toList(messageContext, Message.class);
			if (messages.size() >= 10) {
				messages = messages.subList(1, 10);
			}
			Message currentMessage = Message.builder().content(msg).role(Message.Role.USER).build();
			messages.add(currentMessage);
		} else {
			Message currentMessage = Message.builder().content(msg).role(Message.Role.USER).build();
			messages.add(currentMessage);


		}
		chatGPTStream.streamChatCompletion(messages, eventSourceListener);
		redisService.set(webSocketServer.token,JSONUtil.toJsonStr(messages),300);
	}
	/**
	 * @throws
	 * @title weChatLogin
	 * @description 微信授权登录
	 */
	public Result<UserVo> weChatLogin(WeChatLoginDTO dto) {
		String code = dto.getCode();
		String appid = sysParamsService.getValue("app_wx_login_appid");
		String secret = sysParamsService.getValue("app_wx_login_secret");  //这三个取配置表
		try {
			//通过第一步获得的code获取微信授权信息
			String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appid, secret,code);

			JSONObject jsonObject = HttpUtil.doGetJson(url);
			String openid = jsonObject.getString("openid");
			String unionId = openid;//就当小程序公众号等没有在微信开放平台绑定在一起的情况
			//如果开启了UnionId
			if (StrUtil.isNotBlank(jsonObject.getString("unionid"))) {//取到了 说明绑定了
				unionId = jsonObject.getString("unionid");
			}
			String token = jsonObject.getString("access_token");
			String infoUrl = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", token, openid);
			JSONObject userInfo = HttpUtil.doGetJson(infoUrl);

			//成功获取授权,以下部分为业务逻辑处理了，根据个人业务需求写就可以了
			if (userInfo != null && openid != null) {
				String nickname = userInfo.getString("nickname");
				String headimgurl = userInfo.getString("headimgurl");
				headimgurl = headimgurl.replace("\\", "");
				//根据openid查询时候有用户信息
				UserEntity user = userService.getUserByUnionId(unionId);

				if (user == null) {
					user = new UserEntity(nickname,openid,headimgurl);

					user.setWxUnionid(unionId);
					userService.insert(user);
				}
				//生成token
				String creatToken = userService.creatToken(user);
				UserVo userVo = new UserVo(user);
				userVo.setToken(creatToken);
				return new Result<UserVo>().ok(userVo);
			} else {
				return new Result<UserVo>().error("登录失败");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return new Result<UserVo>().error("登录失败");
		}
	}

	public Result<UserVo> mpWeChatLogin(WeChatLoginDTO dto) {
		try {
			WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
			configStorage.setAppId(sysParamsService.getValue("mp_wx_login_appid"));
			configStorage.setSecret(sysParamsService.getValue("mp_wx_login_secret"));
			configStorage.setToken(sysParamsService.getValue("mp_wx_wechat_token"));
			configStorage.setAesKey(sysParamsService.getValue("mp_wx_wechat_encodingaeskey"));
			WxMpService wxMpService = new WxMpServiceImpl();
			wxMpService.setWxMpConfigStorage(configStorage);
			WxOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.getOAuth2Service().getAccessToken(dto.getCode());
			WxOAuth2UserInfo wxMpUser = wxMpService.getOAuth2Service().getUserInfo(wxMpOAuth2AccessToken, null);
			String openid = wxMpUser.getOpenid();
			String unionId = openid;//就当小程序公众号等没有在微信开放平台绑定在一起的情况
			//如果开启了UnionId
			if (StrUtil.isNotBlank(wxMpUser.getUnionId())) {//取到了 说明绑定了
				unionId = wxMpUser.getUnionId();
			}
			//如果绑定了  那公众号 小程序等同一个用户的unionId是一样的 用这个来判断同一用户避免同一微信在公众号小程序下生成2个用户了
			//如果没绑定 openid和unionId都存openid
			UserEntity user = userService.getUserByUnionId(unionId);
			String nickname = wxMpUser.getNickname();
			String headImgUrl = wxMpUser.getHeadImgUrl();
			if (user == null) {

				user = new UserEntity(nickname,openid,headImgUrl);
				user.setWxUnionid(unionId);
				userService.insert(user);
			}
			//更新openid
			asyncService.updateOpenID(user,openid);
			//生成token
			String creatToken = userService.creatToken(user);
			UserVo userVo = new UserVo(user);
			userVo.setToken(creatToken);
			return new Result<UserVo>().ok(userVo);
		}catch (WxErrorException e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new Result<UserVo>().error("登录失败");
	}

	public Result<UserVo> miniAppLogin(WeChatLoginDTO dto) {
		String code = dto.getCode();
		String encryptedData = dto.getEncryptedData();
		String iv = dto.getIv();
		String miniAppAppid = sysParamsService.getValue("mini_app_appid");
		String miniAppSecret = sysParamsService.getValue("mini_app_secret");
		if (StrUtil.isBlank(miniAppAppid) || StrUtil.isBlank(miniAppSecret)) {
			throw new RenException("请先配置小程序");
		}
		try {
			WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
			config.setMsgDataFormat("JSON");
			config.setAppid(miniAppAppid);
			config.setSecret(miniAppSecret);
			WxMaService wxMaService = new WxMaServiceImpl();
			wxMaService.setWxMaConfig(config);
			WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(code);
			String openid = session.getOpenid();
			String unionId = openid;//就当小程序公众号等没有在微信开放平台绑定在一起的情况
			//如果开启了UnionId
			if (StrUtil.isNotBlank(session.getUnionid())) {//取到了 说明绑定了
				unionId = session.getUnionid();
			}
			WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService()
					.getPhoneNoInfo(session.getSessionKey(), encryptedData, iv);
			String phoneNumber = phoneNoInfo.getPhoneNumber();//用户手机号
			UserEntity user = null;
			if(StrUtil.isNotBlank(phoneNumber)){//取到手机号了
				user = userService.getByMobile(phoneNumber);
				if(user == null){//根据手机号没查到
					//那根据UnionId 查询
					user = userService.getUserByUnionId(unionId);
					//查到了就绑定手机号 没查到就是新用户
					if(user == null){

						user = new UserEntity("微信用户",openid,null);
						user.setWxUnionid(unionId);
						user.setUsername(phoneNumber);
						user.setMobile(phoneNumber);

						userService.insert(user);
					}
				}else{
					//这里是查到了 不管别的  给这狗日的把openid和unionId绑定起
					user.setWxUnionid(unionId);
					user.setWxOpenid(openid);
					userService.updateUnionidAndOpenId(user);
				}
			}else{//没取到手机号
				//先根据unionId查询是否由用户
				user = userService.getUserByUnionId(unionId);
				if(user == null){//没查到就是新用户 查到了直接登录

					user = new UserEntity("微信用户",openid,null);
					user.setWxUnionid(unionId);

					userService.insert(user);
				}
			}
			//生成token
			String creatToken = userService.creatToken(user);
			UserVo userVo = new UserVo(user);
			userVo.setToken(creatToken);
			return new Result<UserVo>().ok(userVo);
		}catch (WxErrorException e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}

	public Result<UserVo> smsLogin(MobileLoginDTO dto) {
		boolean b = verifySmsCode(dto.getMobile(), dto.getCode());
		if(!b){
			throw new RenException("验证码错误");
		}
		//验证码正确
		UserEntity userEntity = userService.getByMobile(dto.getMobile());
		if(userEntity == null){

			//没有绑定用户 就添加新用户
			userEntity = new UserEntity("用户"+SundryUtils.getCardTailNum(dto.getMobile()),null,null);
			userEntity.setUsername(dto.getMobile());
			userEntity.setMobile(dto.getMobile());

			userService.insert(userEntity);
		}
		//生成token
		String creatToken = userService.creatToken(userEntity);
		UserVo userVo = new UserVo(userEntity);
		userVo.setToken(creatToken);
		return new Result<UserVo>().ok(userVo);
	}
	/**
	 * 验证手机短信
	 */
	public boolean verifySmsCode(String mobile, String code) {
		//先判断验证码是否正确
		String smsCode = (String) redisService.get(RedisKeys.SMS_CODE, mobile);
		if (StrUtil.isBlank(smsCode)) {
			return false;
		}
		return smsCode.equals(code);
	}

	public Result sendSms(SendSmsDTO dto) {
		String smsCode = (String) redisService.get(RedisKeys.SMS_CODE,dto.getMobile());
		if (StrUtil.isBlank(smsCode)) {
			smsCode = RandomUtil.getRandomSmsCode();
			redisService.set(RedisKeys.SMS_CODE,dto.getMobile(),smsCode,5 * 60);
		}
//		String s = SendSmsUtils.jgsendSms(dto.getMobile(), smsCode);
//		if(!s.equals("1")){
//			return new Result().error("发送验证码失败");
//		}
//		return new Result().ok("发送成功");
		return new Result().error("发送成功,验证码为:"+smsCode);
	}
}
