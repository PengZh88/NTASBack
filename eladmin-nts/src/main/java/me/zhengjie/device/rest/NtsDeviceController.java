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
package me.zhengjie.device.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.device.domain.NtsDevice;
import me.zhengjie.device.service.NtsDeviceService;
import me.zhengjie.device.service.dto.NtsDeviceQueryCriteria;
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
* @date 2020-12-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "设备信息管理")
@RequestMapping("/api/ntsDevice")
public class NtsDeviceController {

    private final NtsDeviceService ntsDeviceService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsDevice:list')")
    public void download(HttpServletResponse response, NtsDeviceQueryCriteria criteria) throws IOException {
        ntsDeviceService.download(ntsDeviceService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询设备信息")
    @ApiOperation("查询设备信息")
    @PreAuthorize("@el.check('ntsDevice:list')")
    public ResponseEntity<Object> query(NtsDeviceQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsDeviceService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @Log("查询设备信息")
    @ApiOperation("查询设备信息")
    @GetMapping(value = "/ntsAllDevices")
    @PreAuthorize("@el.check('ntsDevice:list')")
    public ResponseEntity<Object> ntsAllDevices() {
        NtsDeviceQueryCriteria criteria = new NtsDeviceQueryCriteria();
        return new ResponseEntity<>(ntsDeviceService.queryAll(criteria), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增设备信息")
    @ApiOperation("新增设备信息")
    @PreAuthorize("@el.check('ntsDevice:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsDevice resources){
        return new ResponseEntity<>(ntsDeviceService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改设备信息")
    @ApiOperation("修改设备信息")
    @PreAuthorize("@el.check('ntsDevice:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsDevice resources){
        ntsDeviceService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除设备信息")
    @ApiOperation("删除设备信息")
    @PreAuthorize("@el.check('ntsDevice:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsDeviceService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}