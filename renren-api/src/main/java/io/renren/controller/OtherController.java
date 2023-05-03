package io.renren.controller;

import io.renren.commom.CommonService;
import io.renren.common.redis.RedisService;
import io.renren.common.utils.Result;
import io.renren.common.validator.ValidatorUtils;
import io.renren.dto.SendSmsDTO;
import io.renren.service.HotQsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 模型类型 热门问题等等各种接口
 *
 * @author Mark sunlightcs@gmail.com
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/other")
public class OtherController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private HotQsService hotQsService;
    @Autowired
    private CommonService commonService;


    @GetMapping("queryHotQs")
    @ApiOperation("查询所有设置的热门问题")
    public Result queryHotQs(){
        return hotQsService.queryHotQs();
    }


    @GetMapping("sendSms")
    @ApiOperation("发送短信接口")
    public Result sendSms(SendSmsDTO dto){
        ValidatorUtils.validateEntity(dto);
        return commonService.sendSms(dto);
    }
}
