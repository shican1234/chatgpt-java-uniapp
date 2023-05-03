package io.renren.modules.questionAnswer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.questionAnswer.dao.QuestionAnswerDao;
import io.renren.modules.questionAnswer.dto.QuestionAnswerDTO;
import io.renren.modules.questionAnswer.entity.QuestionAnswerEntity;
import io.renren.modules.questionAnswer.service.QuestionAnswerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Service
public class QuestionAnswerServiceImpl extends CrudServiceImpl<QuestionAnswerDao, QuestionAnswerEntity, QuestionAnswerDTO> implements QuestionAnswerService {

    @Override
    public QueryWrapper<QuestionAnswerEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<QuestionAnswerEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}