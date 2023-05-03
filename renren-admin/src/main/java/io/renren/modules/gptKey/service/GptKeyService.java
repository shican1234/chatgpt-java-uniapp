package io.renren.modules.gptKey.service;

import io.renren.common.service.CrudService;
import io.renren.modules.gptKey.dto.GptKeyDTO;
import io.renren.modules.gptKey.entity.GptKeyEntity;

import java.util.List;

/**
 * KEY管理
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-12
 */
public interface GptKeyService extends CrudService<GptKeyEntity, GptKeyDTO> {

    List<String> getAllOpenKey();

}