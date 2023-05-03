package io.renren.modules.gptKey.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.gptKey.entity.GptKeyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * KEY管理
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-12
 */
@Mapper
public interface GptKeyDao extends BaseDao<GptKeyEntity> {

    List<String> getAllOpenKey();

}