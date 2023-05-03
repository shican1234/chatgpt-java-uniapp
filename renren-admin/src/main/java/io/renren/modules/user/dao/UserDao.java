package io.renren.modules.user.dao;

import io.renren.common.dao.BaseDao;
import io.renren.modules.user.entity.UserEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2023-04-07
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {
	
	
	/*
	 * 查询出所有的用户
	 * 
	 * */
	List<UserEntity> getUserList();
	
}