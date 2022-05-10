package me.zhengjie.mongoapp.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.mongoapp.service.NtsDboriService;
import me.zhengjie.mongoapp.service.NtsDbprepService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "MongoDB数据接口")
@RequestMapping("/api/ntsMongo")
public class NtsMongoController {
    private final NtsDbprepService ntsDbprepService;
    private final NtsDboriService ntsDboriService;

    @Log("获取MongoDB数据库基本信息")
    @ApiOperation("获取MongoDB数据库基本信息")
    @GetMapping("/mdbinfo")
    public ResponseEntity<Object> mdbInfo() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("dbori", ntsDboriService.getDbInfo());
        data.put("dbprep", ntsDbprepService.getDbInfo());
        // 获取Bar统计数据
        data.putAll(ntsDboriService.barOriStaData());
        data.putAll(ntsDbprepService.barPrepStaData());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @Log("获取MongoDB中预处理Collection")
    @ApiOperation("获取MongoDB中预处理Collection")
    @GetMapping("/prepCollections")
    public ResponseEntity<Object> prepCollections() {
        return new ResponseEntity<>(ntsDbprepService.getCollections(), HttpStatus.OK);
    }
}
