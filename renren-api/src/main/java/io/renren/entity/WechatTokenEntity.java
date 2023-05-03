package io.renren.entity;

import lombok.Data;

/**
 * 描述: 凭证 </br>
 * 发布版本：V1.0 </br>
 */
@Data
public class WechatTokenEntity {
    /**
     * 接口访问凭证֤
     */
    private String accessToken;
    /**
     * 接口访问凭证֤，刷新
     */
    private String refreshToken;
    /**
     * 凭证有效期单位：second
     */
    private int expiresIn;
    /**
     * 授权用户唯一标识
     */
    private String openid;
    /**
     * 微信用户唯一标识
     */
    private String unionId;

}



