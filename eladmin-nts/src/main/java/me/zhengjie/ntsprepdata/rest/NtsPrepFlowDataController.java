/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.ntsprepdata.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.ntsprepdata.domain.NtsPrepFlowData;
import me.zhengjie.ntsprepdata.service.NtsPrepFlowDataService;
import me.zhengjie.ntsprepdata.service.dto.NtsPrepFlowDataQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author Peng Zhan
* @date 2021-02-27
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "预处理流量数据管理")
@RequestMapping("/api/ntsPrepFlowData")
public class NtsPrepFlowDataController {

    private final NtsPrepFlowDataService ntsPrepFlowDataService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsPrepFlowData:list')")
    public void download(HttpServletResponse response, NtsPrepFlowDataQueryCriteria criteria) throws IOException {
        ntsPrepFlowDataService.download(ntsPrepFlowDataService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询预处理流量数据")
    @ApiOperation("查询预处理流量数据")
    @PreAuthorize("@el.check('ntsPrepFlowData:list')")
    public ResponseEntity<Object> query(NtsPrepFlowDataQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsPrepFlowDataService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增预处理流量数据")
    @ApiOperation("新增预处理流量数据")
    @PreAuthorize("@el.check('ntsPrepFlowData:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsPrepFlowData resources){
        return new ResponseEntity<>(ntsPrepFlowDataService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改预处理流量数据")
    @ApiOperation("修改预处理流量数据")
    @PreAuthorize("@el.check('ntsPrepFlowData:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsPrepFlowData resources){
        ntsPrepFlowDataService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除预处理流量数据")
    @ApiOperation("删除预处理流量数据")
    @PreAuthorize("@el.check('ntsPrepFlowData:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsPrepFlowDataService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}