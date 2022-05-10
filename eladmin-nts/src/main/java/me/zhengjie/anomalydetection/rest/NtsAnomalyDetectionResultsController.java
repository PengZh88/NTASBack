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
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionResults;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionResultsService;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionResultsQueryCriteria;
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
* @date 2021-11-20
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "异常检测任务检测结果管理")
@RequestMapping("/api/ntsAnomalyDetectionResults")
public class NtsAnomalyDetectionResultsController {

    private final NtsAnomalyDetectionResultsService ntsAnomalyDetectionResultsService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsAnomalyDetectionResults:list')")
    public void download(HttpServletResponse response, NtsAnomalyDetectionResultsQueryCriteria criteria) throws IOException {
        ntsAnomalyDetectionResultsService.download(ntsAnomalyDetectionResultsService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询异常检测任务检测结果")
    @ApiOperation("查询异常检测任务检测结果")
    @PreAuthorize("@el.check('ntsAnomalyDetectionResults:list')")
    public ResponseEntity<Object> query(NtsAnomalyDetectionResultsQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsAnomalyDetectionResultsService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/getTaskResults/{taskId}")
    @Log("获取异常检测结果数据")
    @ApiOperation("获取异常检测结果数据")
    public ResponseEntity<Object> getTaskResults(@PathVariable Integer taskId) {
        NtsAnomalyDetectionResultsQueryCriteria criteria = new NtsAnomalyDetectionResultsQueryCriteria();
        criteria.setAdTaskId((long) taskId);
        return new ResponseEntity<>(ntsAnomalyDetectionResultsService.queryAll(criteria),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增异常检测任务检测结果")
    @ApiOperation("新增异常检测任务检测结果")
    @PreAuthorize("@el.check('ntsAnomalyDetectionResults:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsAnomalyDetectionResults resources){
        return new ResponseEntity<>(ntsAnomalyDetectionResultsService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改异常检测任务检测结果")
    @ApiOperation("修改异常检测任务检测结果")
    @PreAuthorize("@el.check('ntsAnomalyDetectionResults:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsAnomalyDetectionResults resources){
        ntsAnomalyDetectionResultsService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除异常检测任务检测结果")
    @ApiOperation("删除异常检测任务检测结果")
    @PreAuthorize("@el.check('ntsAnomalyDetectionResults:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsAnomalyDetectionResultsService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}