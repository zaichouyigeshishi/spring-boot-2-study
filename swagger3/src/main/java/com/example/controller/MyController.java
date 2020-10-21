package com.example.controller;

import com.example.bean.MyObject;
import com.example.bean.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author hudongshan
 */
@Api(value = "测试接口组",tags = {"用户","商品"})
@Slf4j
@RestController
@RequestMapping("/v1/simples")
public class MyController {

    @ApiResponses({
            @ApiResponse(responseCode = "200", description="操作成功"),
            @ApiResponse(responseCode = "500", description="系统内部异常")
    })
    @ApiOperation(value = "获取", notes="这是一个获取接口")
    @GetMapping(value = "get", produces = "application/json;charset=UTF-8")
    public ResultData<MyObject> testGet(@RequestParam String p) {
        return ResultData.ok().data(new MyObject("hudongshan","15184433333",18));
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description="操作成功"),
            @ApiResponse(responseCode = "500", description="系统内部异常")
    })
    @ApiOperation(value = "创建", notes="这是一个创建接口")
    @PostMapping(value = "post", produces = "application/json;charset=UTF-8")
    public ResultData testPost() {
        return ResultData.ok().data("ok");
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description="操作成功"),
            @ApiResponse(responseCode = "500", description="系统内部异常")
    })
    @ApiOperation(value = "部分更新", notes="这是一个部分更新接口")
    @PatchMapping(value = "patch", produces = "application/json;charset=UTF-8")
    public ResultData testPatch() {
        return ResultData.ok().data("ok");
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description="操作成功"),
            @ApiResponse(responseCode = "500", description="系统内部异常")
    })
    @ApiOperation(value = "全部更新", notes="这是一个全部更新接口")
    @PutMapping(value = "put", produces = "application/json;charset=UTF-8")
    public ResultData testPut() {
        return ResultData.ok().data("ok");
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description="操作成功"),
            @ApiResponse(responseCode = "500", description="系统内部异常")
    })
    @ApiOperation(value = "删除", notes="这是一个删除接口")
    @DeleteMapping(value = "delete", produces = "application/json;charset=UTF-8")
    public ResultData testDelete() {
        return ResultData.ok().data("ok");
    }

}