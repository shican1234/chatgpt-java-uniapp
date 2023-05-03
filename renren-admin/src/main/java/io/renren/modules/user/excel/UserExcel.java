package io.renren.modules.user.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 用户表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-04-07
 */
@Data
public class UserExcel {
    @Excel(name = "用户id(主键)")
    private Long id;
    @Excel(name = "手机号")
    private String mobile;
    @Excel(name = "普通用户的标识，对当前开发者帐号唯一")
    private String wxOpenid;
    @Excel(name = "用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的")
    private String wxUnionid;
    @Excel(name = "创建时间")
    private Date createTime;
    @Excel(name = "VIP到期时间")
    private Date vipDate;
    @Excel(name = "最后登录时间")
    private Date lastLogin;
    @Excel(name = "0:正常状态 1:封禁状态")
    private Integer status;
    @Excel(name = "封禁原因")
    private String lockReason;
    @Excel(name = "昵称")
    private String nickName;
    @Excel(name = "头像地址")
    private String avatar;
    @Excel(name = "ip")
    private String ip;
    @Excel(name = "渠道号")
    private String channel;
    @Excel(name = "账号(可以为空但是不能重复)")
    private String username;
    @Excel(name = "")
    private String password;

}