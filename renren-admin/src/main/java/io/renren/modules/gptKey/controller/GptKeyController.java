package io.renren.modules.gptKey.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.entity.billing.BillingUsage;
import com.unfbx.chatgpt.entity.billing.Subscription;
import com.unfbx.chatgpt.function.KeyRandomStrategy;
import com.unfbx.chatgpt.interceptor.OpenAILogger;
import io.renren.common.annotation.LogOperation;
import io.renren.common.constant.Constant;
import io.renren.common.exception.RenException;
import io.renren.common.page.PageData;
import io.renren.common.redis.RedisKeys;
import io.renren.common.redis.RedisService;
import io.renren.common.redis.RedisUtils;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.ExcelUtils;
import io.renren.common.utils.Result;
import io.renren.common.validator.AssertUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.gptKey.dto.BalanceDTO;
import io.renren.modules.gptKey.dto.GptKeyDTO;
import io.renren.modules.gptKey.excel.GptKeyExcel;
import io.renren.modules.gptKey.service.GptKeyService;
import io.renren.modules.sys.service.SysParamsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * KEY管理
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-12
 */
@RestController
@RequestMapping("gptKey/gptkey")
@Api(tags="KEY管理")
@Slf4j
public class GptKeyController {
    @Autowired
    private GptKeyService gptKeyService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SysParamsService sysParamsService;
    @Autowired
    private Gson gson;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("gptKey:gptkey:page")
    public Result<PageData<GptKeyDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        PageData<GptKeyDTO> page = gptKeyService.page(params);

        return new Result<PageData<GptKeyDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("gptKey:gptkey:info")
    public Result<GptKeyDTO> get(@PathVariable("id") Long id){
        GptKeyDTO data = gptKeyService.get(id);

        return new Result<GptKeyDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("gptKey:gptkey:save")
    public Result save(@RequestBody GptKeyDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);
        BalanceDTO balanceDTO = getBalanceDTO(dto.getApiKey());
        //保存时候验证
        gptKeyService.save(dto);
        GptKeyRedis();
        return new Result().ok(balanceDTO);
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("gptKey:gptkey:update")
    public Result update(@RequestBody GptKeyDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);
        BalanceDTO balanceDTO = getBalanceDTO(dto.getApiKey());
        gptKeyService.update(dto);
        GptKeyRedis();
        return new Result().ok(balanceDTO);
    }



    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("gptKey:gptkey:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        gptKeyService.delete(ids);
        GptKeyRedis();
        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("gptKey:gptkey:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<GptKeyDTO> list = gptKeyService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, GptKeyExcel.class);
    }
    @GetMapping("queryBalance/{id}")
    @ApiOperation("查询KEY余额")
    public Result<BalanceDTO> queryBalance(@PathVariable("id") Long id){
        GptKeyDTO data = gptKeyService.get(id);
        BalanceDTO balanceDTO = getBalanceDTO(data.getApiKey());
        return new Result<BalanceDTO>().ok(balanceDTO);
    }
    public BalanceDTO getBalanceDTO(String key){
        try {
            OpenAiStreamClient chatGPTStream = getChatGPTStream(key);
            Subscription subscription = chatGPTStream.subscription();
            LocalDate start = DateUtils.dealDays(new Date(),-100);
            BillingUsage billingUsage = chatGPTStream.billingUsage(start, LocalDate.now());
            //剩余多少
            BigDecimal hardLimitUsd = new BigDecimal(subscription.getHardLimitUsd()).setScale(2, BigDecimal.ROUND_HALF_UP);//美元保留2位小数并且四舍五入
            //billingUsage.getTotalUsage()取出来是美分  四舍五入转美元
            BigDecimal totalUsage = billingUsage.getTotalUsage().setScale(2,BigDecimal.ROUND_HALF_UP);
            totalUsage = totalUsage.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_DOWN);
            //余额
            BigDecimal  balance = hardLimitUsd.subtract(totalUsage).setScale(2, BigDecimal.ROUND_DOWN);
            BalanceDTO balanceDTO = new BalanceDTO();
            balanceDTO.setBalance(balance);
            balanceDTO.setTotalUsage(totalUsage);
            balanceDTO.setHardLimitUsd(hardLimitUsd);
            balanceDTO.setAccountName(subscription.getAccountName());
            return balanceDTO;
        }catch (Exception e){
            throw new RenException("KEY校验错误或者链接超时");
        }
    }
    public OpenAiStreamClient getChatGPTStream(String key){
        String chatGptUrl = sysParamsService.getValue("CHAT_GPT_URL");
        if(StrUtil.isBlank(chatGptUrl)){
            chatGptUrl = "https://api.openai.com/";
        }
        OpenAiStreamClient chatGPTStream;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
        //!!!!!!测试或者发布到服务器千万不要配置Level == BODY!!!!
        //!!!!!!测试或者发布到服务器千万不要配置Level == BODY!!!!
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
//                .proxy(proxy)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build();
        chatGPTStream = OpenAiStreamClient
                .builder()
                .apiHost(chatGptUrl)
                .apiKey(Arrays.asList(key))
                //自定义key使用策略 默认随机策略
                .keyStrategy(new KeyRandomStrategy())
                .okHttpClient(okHttpClient)
                .build();
        return chatGPTStream;
    }
    private void GptKeyRedis() {
        //先删除redis里面的GPTKEY
        redisService.deleteKey(RedisKeys.GPT_KEY);
        //查询所有开启的
        List<String> keys = gptKeyService.getAllOpenKey();
        redisService.set(RedisKeys.GPT_KEY,gson.toJson(keys));
    }
}