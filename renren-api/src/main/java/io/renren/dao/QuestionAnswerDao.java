package io.renren.dao;

import io.renren.common.dao.BaseDao;
import io.renren.entity.QuestionAnswerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Mapper
public interface QuestionAnswerDao extends BaseDao<QuestionAnswerEntity> {

    List<QuestionAnswerEntity> queryQuestionAnswerList(Long id);
}