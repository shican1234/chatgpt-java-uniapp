package io.renren.modules.gptKey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.gptKey.dao.GptKeyDao;
import io.renren.modules.gptKey.dto.GptKeyDTO;
import io.renren.modules.gptKey.entity.GptKeyEntity;
import io.renren.modules.gptKey.service.GptKeyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * KEY管理
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-12
 */
@Service
public class GptKeyServiceImpl extends CrudServiceImpl<GptKeyDao, GptKeyEntity, GptKeyDTO> implements GptKeyService {

    @Override
    public QueryWrapper<GptKeyEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<GptKeyEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


    @Override
    public List<String> getAllOpenKey() {
        return baseDao.getAllOpenKey();
    }
}