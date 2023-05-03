package io.renren.modules.questionAnswer.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.questionAnswer.entity.QuestionAnswerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Mapper
public interface QuestionAnswerDao extends BaseDao<QuestionAnswerEntity> {
	
}