package io.renren.modules.hotQs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.service.impl.CrudServiceImpl;
import io.renren.modules.hotQs.dao.HotQsDao;
import io.renren.modules.hotQs.dto.HotQsDTO;
import io.renren.modules.hotQs.entity.HotQsEntity;
import io.renren.modules.hotQs.service.HotQsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 热门问题表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Service
public class HotQsServiceImpl extends CrudServiceImpl<HotQsDao, HotQsEntity, HotQsDTO> implements HotQsService {

    @Override
    public QueryWrapper<HotQsEntity> getWrapper(Map<String, Object> params){
        String id = (String)params.get("id");

        QueryWrapper<HotQsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(id), "id", id);

        return wrapper;
    }


}