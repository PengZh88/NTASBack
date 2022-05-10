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
package me.zhengjie.machineroom.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.machineroom.domain.NtsMachineroom;
import me.zhengjie.machineroom.domain.vo.BuildingMachineroomVo;
import me.zhengjie.machineroom.service.NtsMachineroomService;
import me.zhengjie.machineroom.service.dto.NtsMachineroomQueryCriteria;
import me.zhengjie.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Peng Zhan
 * @website https://el-admin.vip
 * @date 2020-08-06
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "机房信息管理")
@RequestMapping("/api/ntsMachineroom")
public class NtsMachineroomController {

    private final NtsMachineroomService ntsMachineroomService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsMachineroom:list')")
    public void download(HttpServletResponse response, NtsMachineroomQueryCriteria criteria) throws IOException {
        ntsMachineroomService.download(ntsMachineroomService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询机房信息")
    @ApiOperation("查询机房信息")
    @PreAuthorize("@el.check('ntsMachineroom:list')")
    public ResponseEntity<Object> query(NtsMachineroomQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(ntsMachineroomService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增机房信息")
    @ApiOperation("新增机房信息")
    @PreAuthorize("@el.check('ntsMachineroom:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsMachineroom resources) {
        return new ResponseEntity<>(ntsMachineroomService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改机房信息")
    @ApiOperation("修改机房信息")
    @PreAuthorize("@el.check('ntsMachineroom:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsMachineroom resources) {
        ntsMachineroomService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除机房信息")
    @ApiOperation("删除机房信息")
    @PreAuthorize("@el.check('ntsMachineroom:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsMachineroomService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("查询楼宇、机房数据")
    @ApiOperation("查询楼宇、机房树形结构数据")
    @GetMapping(value = "/ntsBuildingAndMachinerooms")
    @PreAuthorize("@el.check('ntsMachineroom:list')")
    public ResponseEntity<Object> ntsBuildingAndMachinerooms() {
        List<BuildingMachineroomVo> bmVos = ntsMachineroomService.queryBMVo();
        return new ResponseEntity<>(PageUtil.toPage(bmVos, bmVos.size()), HttpStatus.OK);
    }
}