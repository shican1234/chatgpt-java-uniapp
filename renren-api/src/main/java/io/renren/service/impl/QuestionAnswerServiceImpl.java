package io.renren.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.dao.QuestionAnswerDao;
import io.renren.entity.QuestionAnswerEntity;
import io.renren.entity.UserEntity;
import io.renren.service.QuestionAnswerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Service
public class QuestionAnswerServiceImpl extends BaseServiceImpl<QuestionAnswerDao, QuestionAnswerEntity> implements QuestionAnswerService {


    @Override
    public List<QuestionAnswerEntity> queryQuestionAnswerList(UserEntity user) {
        return baseDao.queryQuestionAnswerList(user.getId());
    }
}