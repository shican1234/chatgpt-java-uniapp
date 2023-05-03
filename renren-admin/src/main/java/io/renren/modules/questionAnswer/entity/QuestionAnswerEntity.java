package io.renren.modules.questionAnswer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Data
@TableName("tb_question_answer")
public class QuestionAnswerEntity {

    /**
     * 
     */
	private Long id;
    /**
     * 用户id
     */
	private Long userId;
    /**
     * 问题
     */
	private String question;
    /**
     * AI的回答
     */
	private String answer;
    /**
     * 产生时间
     */
	private Date createTime;
}