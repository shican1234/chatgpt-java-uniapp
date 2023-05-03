package io.renren.modules.questionAnswer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Data
@ApiModel(value = "问答记录表")
public class QuestionAnswerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "")
	private Long id;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "问题")
	private String question;

	@ApiModelProperty(value = "AI的回答")
	private String answer;

	@ApiModelProperty(value = "产生时间")
	private Date createTime;
	@ApiModelProperty(value = "用户昵称")
	private String nickName;


}