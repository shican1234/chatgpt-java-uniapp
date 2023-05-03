package io.renren.modules.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-04-07
 */
@Data
@ApiModel(value = "用户表")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id(主键)")
	private Long id;

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "普通用户的标识，对当前开发者帐号唯一")
	private String wxOpenid;

	@ApiModelProperty(value = "用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的")
	private String wxUnionid;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;



	@ApiModelProperty(value = "最后登录时间")
	private Date lastLogin;

	@ApiModelProperty(value = "0:正常状态 1:封禁状态")
	private Integer status;

	@ApiModelProperty(value = "封禁原因")
	private String lockReason;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "头像地址")
	private String avatar;

	@ApiModelProperty(value = "ip")
	private String ip;

	@ApiModelProperty(value = "渠道号")
	private String channel;

	@ApiModelProperty(value = "账号(可以为空但是不能重复)")
	private String username;

	@ApiModelProperty(value = "")
	private String password;


}