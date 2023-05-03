package io.renren.modules.gptKey.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * KEY管理
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-12
 */
@Data
@ApiModel(value = "KEY余额")
public class BalanceDTO implements Serializable {
    private static final long serialVersionUID = 1L;



	@ApiModelProperty(value = "用户名")
	private String accountName;

	@ApiModelProperty(value = "账户总余额(100天内)")
	private BigDecimal hardLimitUsd;
	@ApiModelProperty(value = "已使用的金额")
	private BigDecimal totalUsage;
	@ApiModelProperty(value = "剩余的金额")
	private BigDecimal balance;



}