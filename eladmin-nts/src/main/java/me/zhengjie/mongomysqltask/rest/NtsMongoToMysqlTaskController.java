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
package me.zhengjie.mongomysqltask.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.mongomysqltask.domain.NtsMongoToMysqlTask;
import me.zhengjie.mongomysqltask.service.NtsMongoToMysqlTaskService;
import me.zhengjie.mongomysqltask.service.dto.NtsMongoToMysqlTaskDto;
import me.zhengjie.mongomysqltask.service.dto.NtsMongoToMysqlTaskQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Peng Zhan
 * @website https://el-admin.vip
 * @date 2021-02-20
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "MongoDBMySQL数据同步管理")
@RequestMapping("/api/ntsMongoToMysqlTask")
public class NtsMongoToMysqlTaskController {

    private final NtsMongoToMysqlTaskService ntsMongoToMysqlTaskService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:list')")
    public void download(HttpServletResponse response, NtsMongoToMysqlTaskQueryCriteria criteria) throws IOException {
        ntsMongoToMysqlTaskService.download(ntsMongoToMysqlTaskService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询MongoDBMySQL数据同步")
    @ApiOperation("查询MongoDBMySQL数据同步")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:list')")
    public ResponseEntity<Object> query(NtsMongoToMysqlTaskQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(ntsMongoToMysqlTaskService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping("/findAllTasks")
    @Log("获取全部同步任务")
    @ApiOperation("获取全部同步任务")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:list')")
    public ResponseEntity<Object> findAllTasks() {
        return new ResponseEntity<>(ntsMongoToMysqlTaskService.queryAll(new NtsMongoToMysqlTaskQueryCriteria()), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增MongoDBMySQL数据同步")
    @ApiOperation("新增MongoDBMySQL数据同步")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsMongoToMysqlTask resources) {
        return new ResponseEntity<>(ntsMongoToMysqlTaskService.create(resources), HttpStatus.CREATED);
    }

    @PostMapping("/confirmTask")
    @Log("执行MongoDBMySQL数据同步")
    @ApiOperation("执行MongoDBMySQL数据同步")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:add')")
    public ResponseEntity<Object> confirmTask(@RequestBody Map<String, Integer> data) {
        if (data == null || data.get("id") == null)
            throw new BadRequestException("该任务ID不存在");
        Integer id = data.get("id");
        NtsMongoToMysqlTaskDto dto = ntsMongoToMysqlTaskService.findById((long) id);
        if (!dto.getTaskStatus().equalsIgnoreCase("unexecuted")) {
            throw new BadRequestException("该任务已经执行过，不可重复执行");
        }
        ntsMongoToMysqlTaskService.updateTaskStatusById((long) id, "ongoing");
        ntsMongoToMysqlTaskService.startDataTransform((long) id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    @Log("修改MongoDBMySQL数据同步")
    @ApiOperation("修改MongoDBMySQL数据同步")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsMongoToMysqlTask resources) {
        ntsMongoToMysqlTaskService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除MongoDBMySQL数据同步")
    @ApiOperation("删除MongoDBMySQL数据同步")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        for (long id : ids) {
            NtsMongoToMysqlTaskDto dto = ntsMongoToMysqlTaskService.findById(id);
            if ("ongoing".equals(dto.getTaskStatus()) || "succeeded".equals(dto.getTaskStatus())) {
                throw new BadRequestException("存在正在执行或已执行完成的任务，不可删除！");
            }
        }
        ntsMongoToMysqlTaskService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/getTaskNumbers")
    @Log("统计数据同步任务")
    @ApiOperation("统计数据同步任务")
    @PreAuthorize("@el.check('ntsMongoToMysqlTask:list')")
    public ResponseEntity<Object> getTaskNumbers() {
        List<NtsMongoToMysqlTaskDto> dtos = ntsMongoToMysqlTaskService.queryAll(new NtsMongoToMysqlTaskQueryCriteria());
        Map<String, String> data = new HashMap<>();
        if (dtos != null && !dtos.isEmpty()) {
            data.put("total", String.valueOf(dtos.size()));
            for (NtsMongoToMysqlTaskDto dto : dtos) {
                String status = dto.getTaskStatus();
                if (data.containsKey(status)) {
                    int count = Integer.parseInt(data.get(status)) + 1;
                    data.put(status, String.valueOf(count));
                } else {
                    data.put(status, String.valueOf(1));
                }
            }
        }
        if(!data.containsKey("unexecuted")) {
            data.put("unexecuted", "0");
        }
        if(!data.containsKey("ongoing")) {
            data.put("ongoing", "0");
        }
        if(!data.containsKey("succeeded")) {
            data.put("succeeded", "0");
        }
        if(!data.containsKey("failed")) {
            data.put("failed", "0");
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}