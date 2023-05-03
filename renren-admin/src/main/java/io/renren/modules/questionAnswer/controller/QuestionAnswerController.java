package io.renren.modules.questionAnswer.controller;

import cn.hutool.core.collection.CollUtil;
import io.renren.common.annotation.LogOperation;
import io.renren.common.constant.Constant;
import io.renren.common.page.PageData;
import io.renren.common.utils.ExcelUtils;
import io.renren.common.utils.Result;
import io.renren.common.validator.AssertUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.DefaultGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.questionAnswer.dto.QuestionAnswerDTO;
import io.renren.modules.questionAnswer.excel.QuestionAnswerExcel;
import io.renren.modules.questionAnswer.service.QuestionAnswerService;
import io.renren.modules.user.dto.UserDTO;
import io.renren.modules.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * 问答记录表
 *
 * @author shican 1208296327@qq.com
 * @since 1.0.0 2023-04-10
 */
@RestController
@RequestMapping("questionAnswer/questionanswer")
@Api(tags="问答记录表")
public class QuestionAnswerController {
    @Autowired
    private QuestionAnswerService questionAnswerService;
    @Autowired
    private UserService userService;

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query",required = true, dataType="int") ,
        @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType="String") ,
        @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType="String")
    })
    @RequiresPermissions("questionAnswer:questionanswer:page")
    public Result<PageData<QuestionAnswerDTO>> page(@ApiIgnore @RequestParam Map<String, Object> params){
        params.put("orderField","create_time");
        params.put("order","desc");
        PageData<QuestionAnswerDTO> page = questionAnswerService.page(params);
        List<QuestionAnswerDTO> list = page.getList();
        if(CollUtil.isNotEmpty(list)){
            for(QuestionAnswerDTO dto : list){
                UserDTO userDTO = userService.get(dto.getUserId());
                dto.setNickName(userDTO.getNickName());
            }
        }
        return new Result<PageData<QuestionAnswerDTO>>().ok(page);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    @RequiresPermissions("questionAnswer:questionanswer:info")
    public Result<QuestionAnswerDTO> get(@PathVariable("id") Long id){
        QuestionAnswerDTO data = questionAnswerService.get(id);

        return new Result<QuestionAnswerDTO>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    @RequiresPermissions("questionAnswer:questionanswer:save")
    public Result save(@RequestBody QuestionAnswerDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        questionAnswerService.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    @RequiresPermissions("questionAnswer:questionanswer:update")
    public Result update(@RequestBody QuestionAnswerDTO dto){
        //效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        questionAnswerService.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    @RequiresPermissions("questionAnswer:questionanswer:delete")
    public Result delete(@RequestBody Long[] ids){
        //效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        questionAnswerService.delete(ids);

        return new Result();
    }

    @GetMapping("export")
    @ApiOperation("导出")
    @LogOperation("导出")
    @RequiresPermissions("questionAnswer:questionanswer:export")
    public void export(@ApiIgnore @RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception {
        List<QuestionAnswerDTO> list = questionAnswerService.list(params);

        ExcelUtils.exportExcelToTarget(response, null, list, QuestionAnswerExcel.class);
    }

}