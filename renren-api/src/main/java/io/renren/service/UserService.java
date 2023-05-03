/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.service;

import io.renren.common.service.BaseService;
import io.renren.entity.UserEntity;
import io.renren.dto.LoginDTO;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends BaseService<UserEntity> {

	UserEntity getByMobile(String mobile);

	UserEntity getUserByUserId(Long userId);

	/**
	 * 用户登录
	 * @param dto    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> login(LoginDTO dto);

    void updateMsgCount(Long userId, int count);

    UserEntity getUserByOpenId(String openid);

	String creatToken(UserEntity user);

    UserEntity getUserByUnionId(String unionId);

	void updateUnionidAndOpenId(UserEntity user);

	void updateMobile(UserEntity user);

	int countUserByPid(Long id);

	int countUserByGpid(Long id);

	List<UserEntity> getUserListByPid(Long id);
}
