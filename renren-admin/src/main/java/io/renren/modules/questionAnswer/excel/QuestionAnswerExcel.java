package io.renren.modules.questionAnswer.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Data
public class QuestionAnswerExcel {
    @Excel(name = "")
    private Long id;
    @Excel(name = "用户id")
    private Integer userId;
    @Excel(name = "问题")
    private String question;
    @Excel(name = "AI的回答")
    private String answer;
    @Excel(name = "产生时间")
    private Date createTime;

}