

package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel(value = "手机验证码登入注册参数")
public class MobileLoginDTO {
    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;
    @ApiModelProperty(value = "验证码")
    @NotBlank(message="验证码不能为空")
    private String code;

    @ApiModelProperty(value = "邀请ID")
    private Long pid;


}