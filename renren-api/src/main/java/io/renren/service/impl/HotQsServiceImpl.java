package io.renren.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.utils.Result;
import io.renren.dao.HotQsDao;
import io.renren.entity.HotQsEntity;
import io.renren.service.HotQsService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 热门问题表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Service
public class HotQsServiceImpl extends BaseServiceImpl<HotQsDao, HotQsEntity> implements HotQsService {


    @Override
    public Result queryHotQs() {
        QueryWrapper<HotQsEntity> wrapper = new QueryWrapper<>();
        wrapper.lambda().orderByDesc(HotQsEntity::getCreateTime);
        List<HotQsEntity> hotQsEntities = baseDao.selectList(wrapper);
        if(CollUtil.isNotEmpty(hotQsEntities)){
            Collections.shuffle(hotQsEntities);
        }
        return new Result().ok(hotQsEntities);
    }
}