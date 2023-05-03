package io.renren.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.renren.common.utils.DateUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-04-07
 */
@Data
@TableName("tb_user")
public class UserEntity {

	/**
	 * 用户id(主键)
	 */
	private Long id;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 普通用户的标识，对当前开发者帐号唯一
	 */
	private String wxOpenid;
	/**
	 * 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的
	 */
	private String wxUnionid;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后登录时间
	 */
	private Date lastLogin;
	/**
	 * 0:正常状态 1:封禁状态
	 */
	private Integer status;
	/**
	 * 封禁原因
	 */
	private String lockReason;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像地址
	 */
	private String avatar;
	/**
	 * ip
	 */
	private String ip;

	/**
	 * 账号(可以为空但是不能重复,丢弃不用)
	 */
	private String username;
	/**
	 *密码(丢弃不用)
	 */
	private String password;



	public UserEntity(){}
	public UserEntity(String nickName,String wxOpenid,String avatar){
		this.nickName = nickName;
		if(StrUtil.isNotBlank(wxOpenid)){
			this.wxOpenid = wxOpenid;
		}
		this.avatar = avatar;
		this.createTime = new Date();
		this.lastLogin = new Date();
		this.status = 0;
	}
}