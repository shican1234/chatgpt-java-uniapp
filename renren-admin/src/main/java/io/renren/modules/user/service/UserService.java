package io.renren.modules.user.service;

import java.util.List;

import io.renren.common.service.CrudService;
import io.renren.modules.user.dto.UserDTO;
import io.renren.modules.user.entity.UserEntity;

/**
 * 用户表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-04-07
 */
public interface UserService extends CrudService<UserEntity, UserDTO> {
	
	/*
	 * 查询出所有的用户
	 * 
	 * */
	List<UserEntity> getUserList();

}