

package io.renren.utils;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import io.renren.service.SysParamsService;


/**
 * 阿里云短信工具类
 *
 * @author Mark helin
 */
@Component
public class SendSmsUtils {
	
	 // 声明对象
    public static SendSmsUtils sendSmsUtils;
	@Autowired
	private SysParamsService sysParamsService;
    
    @PostConstruct // 初始化
    public void init(){
    	sendSmsUtils = this;
    	sendSmsUtils.sysParamsService = this.sysParamsService;
    }

	
//	protected static final String AccessKeyID = "LTAI4GF5tbuEBiFSGNjC5Gxo";//阿里云短信ID
//	protected static final String AccessSecret = "fg14QYS8uW3zqE3W6apzQLI7ytW9in";//阿里云短信私钥
//	protected static final String AccessKeyID = "LTAI5t7RxLMjtng2hq9L99mV";//阿里云短信ID
//	protected static final String AccessSecret = "5QfwSfwhAnjKtVsnHuXEbGVItgP6GJ";//阿里云短信私钥
//	  public static String SendSms(String phone,String TemplateCode,int smscode,int type) throws com.aliyuncs.exceptions.ClientException {
//		  	DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", AccessKeyID, AccessSecret);
//	        IAcsClient client = new DefaultAcsClient(profile);
//
//	        CommonRequest request = new CommonRequest();
//	        request.setMethod(MethodType.POST);
//	        request.setDomain("dysmsapi.aliyuncs.com");//阿里云短信API接口
//	        request.setVersion("2017-05-25");
//	        request.setAction("SendSms");//标记
//	        request.putQueryParameter("RegionId", "cn-hangzhou");//短信国家(中国)
//	        request.putQueryParameter("PhoneNumbers", phone);//接收手机号
////	        request.putQueryParameter("SignName", "卿缘科技");//审核标记
//	        request.putQueryParameter("SignName", "巧聊");//审核标记
//	        request.putQueryParameter("TemplateCode", TemplateCode);//发送短信类型
//	        request.putQueryParameter("TemplateParam", "{code:"+smscode+"}");//发送短信验证码(json格式)
//	        try {
//	            CommonResponse response = client.getCommonResponse(request);
//	            String data = response.getData();
//	            JSONObject object = JSONObject.fromObject(data);
//	            String code = object.getString("Code");;
//				if(code.equals("OK")){
//					return "1";
//				}else {
//					return "2";
//				}
//	        } catch (ServerException e) {
//	            e.printStackTrace();
//	        } catch (ClientException e) {
//	            e.printStackTrace();
//	        }
//			return null;
//	    }
	  
	  public static String jgsendSms(String mobile, String code) {
//		   String appKey = "be28096359d877d470517297";
//	       String masterSecret = "9e5dc7eaeccd2e39bc3666b4";
		  //获取极光短信appKey
		  String appKey = sendSmsUtils.sysParamsService .getValue("JG_APP_KEY");
		  //获取极光短信masterSecret
		  String masterSecret = sendSmsUtils.sysParamsService .getValue("JG_SERCET");
	       //初始化发短信客户端
	       SMSClient smsClient = new SMSClient(masterSecret, appKey);
//		   NetEaseSmsRet netEaseSmsRet = new NetEaseSmsRet();
	      try{
	      	 SMSPayload payload = SMSPayload.newBuilder()
	                   .setMobileNumber(mobile) // 手机号码
	                   .setTempId(1)            // 短信模板ID 需要自行申请 模板id为：1的则自带验证码模板id
	                   .addTempPara("code", code)  // key模板参数value：参数值  您的手机验证码：{{code}}，有效期5分钟，请勿泄露。如非本人操作，请忽略此短信。谢谢！
	                   .setSignId(24976)// 签名id 需要自行申请审核。个人也可以申请
	                   .build();

	           //发送短信 会返回msg_id
	           SendSMSResult res = smsClient.sendTemplateSMS(payload);
	           String messageId = res.getMessageId();
	           if(isNumeric(messageId)) {
		           //指向保存短信发送记录业务逻辑
		           /**
		            * 第一个参数极光返回的消息id
		            * 第二个发送的手机号
		            * 第三个发送内容
		            * 第四个发送时间
		            * 保存到DB
		            */
	        	   //短信发送成功
	        	   return "1";
	           }else {
	        	   return "2";
			}
	           //执行业务/

	           
	      } catch (Exception e) {
	      }
	      return null;
	  }
	  
	    public static boolean isNumeric(String str){
	        for (int i = str.length();--i>=0;){ 
	           if (!Character.isDigit(str.charAt(i))){
	                return false;
	          }
	        }
	        return true;
	     }
	    
	    public static void main(String[] args) {
	    	String jgsendSms = jgsendSms("17188399444", "1234");
	    	if(jgsendSms != null && jgsendSms.equals("1")) {
	    		System.out.println("验证码发送成功!");
	    	}else {
				System.out.println("验证码发送失败!");
			}
		}

}
