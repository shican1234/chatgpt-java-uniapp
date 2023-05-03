package io.renren.service;

import io.renren.common.service.BaseService;
import io.renren.common.utils.Result;
import io.renren.entity.HotQsEntity;

/**
 * 热门问题表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
public interface HotQsService extends BaseService<HotQsEntity> {

    Result queryHotQs();

}