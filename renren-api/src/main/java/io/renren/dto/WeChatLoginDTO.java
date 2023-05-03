/**
 /**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel(value = "微信登入提交参数")
public class WeChatLoginDTO {
    @ApiModelProperty(value = "code")
    @NotBlank(message="code不能为空")
    private String code;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "小程序完整用户信息的加密数据,这个值是open-type='getPhoneNumber' 这个里面返回的")
    private String encryptedData;

    @ApiModelProperty(value = "小程序加密算法的初始向量,这个值是open-type='getPhoneNumber' 这个里面返回的")
    private String iv;
}