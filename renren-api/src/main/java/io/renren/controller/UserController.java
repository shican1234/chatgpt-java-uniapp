/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.controller;

import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.UpdateUserDTO;
import io.renren.entity.QuestionAnswerEntity;
import io.renren.entity.UserEntity;
import io.renren.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 测试接口
 *
 * @author Mark sunlightcs@gmail.com
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@Api(tags="用户信息等各种")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionAnswerService questionAnswerService;
    @Autowired
    private SysParamsService sysParamsService;

    @Login
    @GetMapping("userInfo")
    @ApiOperation(value="获取用户信息", response=UserEntity.class)
    public Result<UserEntity> userInfo(@ApiIgnore @LoginUser UserEntity user){
        return new Result<UserEntity>().ok(user);
    }


    @Login
    @PostMapping("update")
    @ApiOperation(value="修改用户信息", response=UserEntity.class)
    public Result<UserEntity> update(@ApiIgnore @LoginUser UserEntity user,@RequestBody UpdateUserDTO dto){
        ValidatorUtils.validateEntity(dto);
        UserEntity updateUser = new UserEntity();
        updateUser.setId(user.getId());
        updateUser.setNickName(dto.getNickName());
        updateUser.setAvatar(dto.getAvatar());
        userService.updateById(updateUser);
        return new Result<UserEntity>().ok(user);
    }

    @Login
    @GetMapping("queryQuestionAnswerList")
    @ApiOperation(value="查询问答记录,只返回最新500条", response= QuestionAnswerEntity.class)
    public Result<List<QuestionAnswerEntity>> queryQuestionAnswerList(@LoginUser UserEntity user){

        List<QuestionAnswerEntity> list = questionAnswerService.queryQuestionAnswerList(user);
        return new Result<List<QuestionAnswerEntity>>().ok(list);
    }



}