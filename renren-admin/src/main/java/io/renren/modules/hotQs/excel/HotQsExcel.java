package io.renren.modules.hotQs.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 热门问题表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@Data
public class HotQsExcel {
    @Excel(name = "ID")
    private Long id;
    @Excel(name = "热门问题内容")
    private String hotQs;
    @Excel(name = "创建时间")
    private Date createTime;

}