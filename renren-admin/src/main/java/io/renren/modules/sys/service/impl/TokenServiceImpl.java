/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.modules.sys.service.impl;

import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.modules.sys.dao.TokenDao;
import io.renren.modules.sys.entity.TokenEntity;
import io.renren.modules.sys.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class TokenServiceImpl extends BaseServiceImpl<TokenDao, TokenEntity> implements TokenService {
	@Autowired
    private TokenDao tokenDao;

	private String generateToken(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	@Override
	public TokenEntity getByToken(String token) {
		TokenEntity byToken = tokenDao.getByToken(token);
		return byToken;
	}

	@Override
	public TokenEntity getByUserId(Long userId) {
		TokenEntity byUserId = tokenDao.getByUserId(userId);
		return byUserId;
	}
}
