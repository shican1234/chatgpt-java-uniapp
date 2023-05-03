/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.service.impl;

import io.renren.common.exception.ErrorCode;
import io.renren.common.exception.RenException;
import io.renren.common.service.impl.BaseServiceImpl;
import io.renren.common.validator.AssertUtils;
import io.renren.dao.UserDao;
import io.renren.entity.TokenEntity;
import io.renren.entity.UserEntity;
import io.renren.dto.LoginDTO;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;


	@Override
	public UserEntity getByMobile(String mobile) {
		return baseDao.getUserByMobile(mobile);
	}

	@Override
	public UserEntity getUserByUserId(Long userId) {
		return baseDao.getUserByUserId(userId);
	}

	@Override
	public Map<String, Object> login(LoginDTO dto) {
		UserEntity user = getByMobile(dto.getMobile());
		AssertUtils.isNull(user, ErrorCode.ACCOUNT_PASSWORD_ERROR);

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(dto.getPassword()))){
			throw new RenException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
		}

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireDate().getTime() - System.currentTimeMillis());

		return map;
	}

	@Override
	public void updateMsgCount(Long userId, int count) {
		Map<String,Object> map = new HashMap<>();
		map.put("userId",userId);
		map.put("msgCount",count);
		baseDao.updateMsgCount(map);
	}

	@Override
	public UserEntity getUserByOpenId(String openid) {
		return baseDao.getUserByOpenId(openid);
	}

	@Override
	public String creatToken(UserEntity user) {
		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getId());

		return tokenEntity.getToken();
	}

	@Override
	public UserEntity getUserByUnionId(String unionId) {
		return baseDao.getUserByUnionId(unionId);
	}

	@Override
	public void updateUnionidAndOpenId(UserEntity user) {
		UserEntity nUserEntity = new UserEntity();
		nUserEntity.setId(user.getId());
		nUserEntity.setWxUnionid(user.getWxUnionid());
		nUserEntity.setWxOpenid(user.getWxOpenid());
		updateById(nUserEntity);
	}

	@Override
	public void updateMobile(UserEntity user) {
		UserEntity nUserEntity = new UserEntity();
		nUserEntity.setId(user.getId());
		nUserEntity.setMobile(user.getMobile());
		nUserEntity.setUsername(user.getUsername());
		updateById(nUserEntity);
	}

	@Override
	public int countUserByPid(Long id) {
		return baseDao.countUserByPid(id);
	}

	@Override
	public int countUserByGpid(Long id) {
		return baseDao.countUserByGpid(id);
	}

	@Override
	public List<UserEntity> getUserListByPid(Long id) {
		return baseDao.getUserListByPid(id);
	}

}