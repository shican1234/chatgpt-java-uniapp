/**
 * Copyright (c) 2018 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.renren.controller;


import cn.hutool.core.util.StrUtil;
import io.renren.annotation.Login;
import io.renren.commom.CommonService;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.LoginDTO;
import io.renren.dto.MobileLoginDTO;
import io.renren.dto.WeChatLoginDTO;
import io.renren.service.SysParamsService;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import io.renren.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/oauth")
@Api(tags="登录接口")
public class OauthController {
    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;
    @Autowired
    private SysParamsService sysParamsService;
    @PostMapping("appWeChatLogin")
    @ApiOperation("APP微信快捷登陆")
    public Result<UserVo> appWeChatLogin(@RequestBody WeChatLoginDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        return commonService.weChatLogin(dto);
    }


    @PostMapping("mpWeChatLogin")
    @ApiOperation("微信公众号授权登陆")
    public Result<UserVo> mpWeChatLogin(@RequestBody WeChatLoginDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        return commonService.mpWeChatLogin(dto);
    }
    @GetMapping("getWxGZHAppid")
    @ApiOperation("微信公众号登录获取公众号appid")
    public Result<String> getWxGZHAppid(){
        //表单校验
        String mpWxLoginAppid = sysParamsService.getValue("mp_wx_login_appid");
        if(StrUtil.isBlank(mpWxLoginAppid)){
            return new Result<String>().error("暂未配置公众号信息");
        }
        return new Result<String>().ok(mpWxLoginAppid);
    }
    @PostMapping("miniAppLogin")
    @ApiOperation("小程序登陆")
    public Result<UserVo> miniAppLogin(@RequestBody WeChatLoginDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        return commonService.miniAppLogin(dto);
    }

    @PostMapping("smsLogin")
    @ApiOperation("验证码登录注册")
    public Result<UserVo> smsLogin(@RequestBody MobileLoginDTO dto){
        //表单校验
        ValidatorUtils.validateEntity(dto);
        return commonService.smsLogin(dto);
    }

}