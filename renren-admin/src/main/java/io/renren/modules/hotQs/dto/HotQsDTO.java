package io.renren.modules.hotQs.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 热门问题表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Data
@ApiModel(value = "热门问题表")
public class HotQsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID")
	private Long id;

	@ApiModelProperty(value = "热门问题内容")
	private String hotQs;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;


}