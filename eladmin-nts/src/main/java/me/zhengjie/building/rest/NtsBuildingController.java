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
package me.zhengjie.building.rest;

import me.zhengjie.building.service.NtsBuildingService;
import me.zhengjie.annotation.Log;
import me.zhengjie.building.domain.NtsBuilding;
import me.zhengjie.building.service.dto.NtsBuildingQueryCriteria;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.machineroom.service.NtsMachineroomService;
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
* @date 2020-08-05
**/
@RestController
@Api(tags = "楼宇信息管理")
@RequestMapping("/api/ntsBuilding")
@RequiredArgsConstructor
public class NtsBuildingController {

    private final NtsBuildingService ntsBuildingService;
    private final NtsMachineroomService ntsMachineroomService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsBuilding:list')")
    public void download(HttpServletResponse response, NtsBuildingQueryCriteria criteria) throws IOException {
        ntsBuildingService.download(ntsBuildingService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询楼宇信息")
    @ApiOperation("查询楼宇信息")
    @PreAuthorize("@el.check('ntsBuilding:list')")
    public ResponseEntity<Object> query(NtsBuildingQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsBuildingService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增楼宇信息")
    @ApiOperation("新增楼宇信息")
    @PreAuthorize("@el.check('ntsBuilding:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsBuilding resources){
        return new ResponseEntity<>(ntsBuildingService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改楼宇信息")
    @ApiOperation("修改楼宇信息")
    @PreAuthorize("@el.check('ntsBuilding:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsBuilding resources){
        ntsBuildingService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除楼宇信息")
    @ApiOperation("删除楼宇信息")
    @PreAuthorize("@el.check('ntsBuilding:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        for(Long id : ids) {
            Integer count = ntsMachineroomService.countByBuildingId(id);
            if(count > 0) {
                throw new BadRequestException("当前楼宇存在关联的机房信息，请先删除相应机房！");
            }
        }
        ntsBuildingService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}