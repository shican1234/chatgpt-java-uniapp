package io.renren.modules.gptKey.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * KEY管理
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-12
 */
@Data
@ApiModel(value = "KEY管理")
public class GptKeyDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "GPT的KEY")
	private String apiKey;

	@ApiModelProperty(value = "状态(0启用1关闭)")
	private Integer status;
	@ApiModelProperty(value = "备注")
	private String remarks;


}