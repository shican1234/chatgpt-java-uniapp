package io.renren.service;

import io.renren.common.service.BaseService;
import io.renren.entity.QuestionAnswerEntity;
import io.renren.entity.UserEntity;

import java.util.List;

/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
public interface QuestionAnswerService extends BaseService<QuestionAnswerEntity> {

    List<QuestionAnswerEntity> queryQuestionAnswerList(UserEntity user);
}