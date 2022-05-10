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
package me.zhengjie.carbinet.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.carbinet.domain.NtsCarbinet;
import me.zhengjie.carbinet.domain.vo.MachineroomCarbinetVo;
import me.zhengjie.carbinet.service.NtsCarbinetService;
import me.zhengjie.carbinet.service.dto.NtsCarbinetDto;
import me.zhengjie.carbinet.service.dto.NtsCarbinetQueryCriteria;
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
* @website https://el-admin.vip
* @author Peng Zhan
* @date 2020-08-18
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "机柜信息管理")
@RequestMapping("/api/ntsCarbinet")
public class NtsCarbinetController {

    private final NtsCarbinetService ntsCarbinetService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsCarbinet:list')")
    public void download(HttpServletResponse response, NtsCarbinetQueryCriteria criteria) throws IOException {
        ntsCarbinetService.download(ntsCarbinetService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询机柜信息")
    @ApiOperation("查询机柜信息")
    @PreAuthorize("@el.check('ntsCarbinet:list')")
    public ResponseEntity<Object> query(NtsCarbinetQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsCarbinetService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/ntsMachineroomsandCarbinets")
    @Log("查询机柜信息")
    @ApiOperation("查询机柜信息")
    @PreAuthorize("@el.check('ntsCarbinet:list')")
    public ResponseEntity<Object> ntsMachineroomsandCarbinets() {
        List<NtsCarbinetDto> mcVos = ntsCarbinetService.queryAll(new NtsCarbinetQueryCriteria());
        return new ResponseEntity<>(PageUtil.toPage(mcVos, mcVos.size()), HttpStatus.OK);
    }

    @PostMapping
    @Log("新增机柜信息")
    @ApiOperation("新增机柜信息")
    @PreAuthorize("@el.check('ntsCarbinet:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsCarbinet resources){
        return new ResponseEntity<>(ntsCarbinetService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改机柜信息")
    @ApiOperation("修改机柜信息")
    @PreAuthorize("@el.check('ntsCarbinet:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsCarbinet resources){
        ntsCarbinetService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除机柜信息")
    @ApiOperation("删除机柜信息")
    @PreAuthorize("@el.check('ntsCarbinet:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsCarbinetService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}