/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.dao;

import io.renren.common.dao.BaseDao;
import io.renren.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {
    UserEntity getUserByMobile(String mobile);

    UserEntity getUserByUserId(Long userId);

    void updateMsgCount(Map<String, Object> map);

    UserEntity getUserByOpenId(String openid);

    UserEntity getUserByUnionId(String unionId);

    int countUserByPid(Long id);

    int countUserByGpid(Long id);

    List<UserEntity> getUserListByPid(Long id);
}
