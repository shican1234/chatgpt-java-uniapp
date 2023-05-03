

package io.renren.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@ApiModel(value = "修改用户信息注册参数")
public class UpdateUserDTO {
    @ApiModelProperty(value = "昵称")
    @NotBlank(message="昵称不能为空")
    private String nickName;
    @ApiModelProperty(value = "头像")
    @NotBlank(message="头像不能为空")
    private String avatar;


}