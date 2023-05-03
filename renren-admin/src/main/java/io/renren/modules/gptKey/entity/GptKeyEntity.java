package io.renren.modules.gptKey.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * KEY管理
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-12
 */
@Data
@TableName("tb_gpt_key")
public class GptKeyEntity {

    /**
     * ID
     */
	private Long id;
    /**
     * GPT的KEY
     */
	private String apiKey;
    /**
     * 状态(0启用1关闭)
     */
	private Integer status;
    /**
     * 备注
     */
    private String remarks;
}