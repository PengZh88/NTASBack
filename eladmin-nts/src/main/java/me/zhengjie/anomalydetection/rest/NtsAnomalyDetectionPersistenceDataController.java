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
package me.zhengjie.anomalydetection.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionPersistenceData;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionPersistenceDataService;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionPersistenceDataQueryCriteria;
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
* @date 2021-11-12
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "异常检测任务持久化待检测数据管理")
@RequestMapping("/api/ntsAnomalyDetectionPersistenceData")
public class NtsAnomalyDetectionPersistenceDataController {

    private final NtsAnomalyDetectionPersistenceDataService ntsAnomalyDetectionPersistenceDataService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsAnomalyDetectionPersistenceData:list')")
    public void download(HttpServletResponse response, NtsAnomalyDetectionPersistenceDataQueryCriteria criteria) throws IOException {
        ntsAnomalyDetectionPersistenceDataService.download(ntsAnomalyDetectionPersistenceDataService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询异常检测任务持久化待检测数据")
    @ApiOperation("查询异常检测任务持久化待检测数据")
    @PreAuthorize("@el.check('ntsAnomalyDetectionPersistenceData:list')")
    public ResponseEntity<Object> query(NtsAnomalyDetectionPersistenceDataQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsAnomalyDetectionPersistenceDataService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增异常检测任务持久化待检测数据")
    @ApiOperation("新增异常检测任务持久化待检测数据")
    @PreAuthorize("@el.check('ntsAnomalyDetectionPersistenceData:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsAnomalyDetectionPersistenceData resources){
        return new ResponseEntity<>(ntsAnomalyDetectionPersistenceDataService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改异常检测任务持久化待检测数据")
    @ApiOperation("修改异常检测任务持久化待检测数据")
    @PreAuthorize("@el.check('ntsAnomalyDetectionPersistenceData:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsAnomalyDetectionPersistenceData resources){
        ntsAnomalyDetectionPersistenceDataService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除异常检测任务持久化待检测数据")
    @ApiOperation("删除异常检测任务持久化待检测数据")
    @PreAuthorize("@el.check('ntsAnomalyDetectionPersistenceData:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsAnomalyDetectionPersistenceDataService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}