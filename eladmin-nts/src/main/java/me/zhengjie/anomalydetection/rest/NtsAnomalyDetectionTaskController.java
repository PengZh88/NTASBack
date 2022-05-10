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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import me.zhengjie.annotation.Log;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionParameters;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionTask;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionParametersService;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionPersistenceDataService;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionTaskService;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionPersistenceDataDto;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionTaskDto;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionTaskQueryCriteria;
import me.zhengjie.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Peng Zhan
 * @website https://el-admin.vip
 * @date 2021-03-10
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "流量异常检测管理")
@RequestMapping("/api/ntsAnomalyDetectionTask")
public class NtsAnomalyDetectionTaskController {

    private final NtsAnomalyDetectionTaskService ntsAnomalyDetectionTaskService;
    private final NtsAnomalyDetectionParametersService ntsAnomalyDetectionParametersService;
    private final NtsAnomalyDetectionPersistenceDataService ntsAnomalyDetectionPersistenceDataService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsAnomalyDetectionTask:list')")
    public void download(HttpServletResponse response, NtsAnomalyDetectionTaskQueryCriteria criteria) throws IOException {
        ntsAnomalyDetectionTaskService.download(ntsAnomalyDetectionTaskService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询流量异常检测")
    @ApiOperation("查询流量异常检测")
    @PreAuthorize("@el.check('ntsAnomalyDetectionTask:list')")
    public ResponseEntity<Object> query(NtsAnomalyDetectionTaskQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(ntsAnomalyDetectionTaskService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增流量异常检测")
    @ApiOperation("新增流量异常检测")
    @PreAuthorize("@el.check('ntsAnomalyDetectionTask:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsAnomalyDetectionTask resources) {
        resources.setDrillStyle("N");
        NtsAnomalyDetectionTaskDto dto = ntsAnomalyDetectionTaskService.create(resources);
        Long taskId = dto.getId();
        ntsAnomalyDetectionParametersService.createObjs(taskId, resources.getAlgParamDatas());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @PostMapping("/confirmTask")
    @Log("执行启动异常检测任务")
    @ApiOperation("执行启动异常检测任务")
    @PreAuthorize("@el.check('ntsAnomalyDetectionTask:add')")
    public ResponseEntity<Object> confirmTask(@RequestBody Map<String, Integer> data) {
        if (data == null || data.get("id") == null)
            throw new BadRequestException("该任务ID不存在");
        Integer id = data.get("id");
        NtsAnomalyDetectionTaskDto task = ntsAnomalyDetectionTaskService.findById((long) id);
        String started = task.getStartNow();
        if (StrUtil.isNotEmpty(started) && !"N".equalsIgnoreCase(started)) {
            // 已经启动了，无法再次启动
            throw new BadRequestException("该任务已经执行过，不可重复执行");
        }
        // 持久化数据
        ntsAnomalyDetectionTaskService.startAnomalyTask((long) id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/viewTask/{id}")
    @Log("查看异常检测任务")
    @ApiOperation("查看异常检测任务")
    public ResponseEntity<Object> viewTask(@PathVariable Integer id) {
        NtsAnomalyDetectionTaskDto task = ntsAnomalyDetectionTaskService.findById((long) id);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("tdata", task);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/viewTaskAlgParameters/{taskId}")
    @Log("查看异常检测关联算法参数")
    @ApiOperation("查看异常检测关联算法参数")
    public ResponseEntity<Object> viewTaskAlgParameters(@PathVariable Integer taskId) {
        Map<String, Object> data = new HashMap<String, Object>();
        List<NtsAnomalyDetectionParameters> parameters = ntsAnomalyDetectionParametersService.queryByTaskId((long) taskId);
        data.put("data", parameters);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping(value = "/getTaskFlowChartData/{taskId}")
    @Log("获取异常检测数据")
    @ApiOperation("获取异常检测数据")
    public ResponseEntity<Object> getTaskFlowChartData(@PathVariable Integer taskId) {
        Map<String, Object> data = new HashMap<String, Object>();
        NtsAnomalyDetectionTaskDto taskDto = ntsAnomalyDetectionTaskService.findById((long) taskId);
        Timestamp ntsStart = taskDto.getNtsStart();
        long timemi = ntsStart.getTime();
        data.put("startTime", timemi);
        data.put("fineGrained", taskDto.getFineGrained());
        NtsAnomalyDetectionPersistenceDataDto pdto = ntsAnomalyDetectionPersistenceDataService.findByAdTaskId((long) taskId);
        data.put("viewData", pdto);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PutMapping
    @Log("修改流量异常检测")
    @ApiOperation("修改流量异常检测")
    @PreAuthorize("@el.check('ntsAnomalyDetectionTask:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsAnomalyDetectionTask resources) {
        ntsAnomalyDetectionTaskService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除流量异常检测")
    @ApiOperation("删除流量异常检测")
    @PreAuthorize("@el.check('ntsAnomalyDetectionTask:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsAnomalyDetectionTaskService.deleteAll(ids);
        for (Long id : ids) {
            ntsAnomalyDetectionParametersService.deleteByTaskId(id);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}