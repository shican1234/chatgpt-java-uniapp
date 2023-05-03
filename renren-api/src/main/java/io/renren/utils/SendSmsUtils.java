

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


	  public static String jgsendSms(String mobile, String code) {

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
	    	String jgsendSms = jgsendSms("18112345678", "1234");
	    	if(jgsendSms != null && jgsendSms.equals("1")) {
	    		System.out.println("验证码发送成功!");
	    	}else {
				System.out.println("验证码发送失败!");
			}
		}

}
