package io.renren.modules.hotQs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 热门问题表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Data
@TableName("tb_hot_qs")
public class HotQsEntity {

    /**
     * ID
     */
	private Long id;
    /**
     * 热门问题内容
     */
	private String hotQs;
    /**
     * 创建时间
     */
	private Date createTime;
}