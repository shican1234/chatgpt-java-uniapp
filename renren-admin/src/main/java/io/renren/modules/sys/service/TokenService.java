/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service;

import io.renren.common.service.BaseService;
import io.renren.modules.sys.entity.TokenEntity;

/**
 * 用户Token
 * 
 * @author Mark sunlightcs@gmail.com
 */
public interface TokenService extends BaseService<TokenEntity> {
	
	   TokenEntity getByToken(String token);

	    TokenEntity getByUserId(Long userId);


}
