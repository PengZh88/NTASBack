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
package me.zhengjie.ipportal.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.ipportal.domain.NtsIpPortal;
import me.zhengjie.ipportal.service.NtsIpPortalService;
import me.zhengjie.ipportal.service.dto.NtsIpPortalQueryCriteria;
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
* @date 2021-01-07
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "IP协议号管理")
@RequestMapping("/api/ntsIpPortal")
public class NtsIpPortalController {

    private final NtsIpPortalService ntsIpPortalService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsIpPortal:list')")
    public void download(HttpServletResponse response, NtsIpPortalQueryCriteria criteria) throws IOException {
        ntsIpPortalService.download(ntsIpPortalService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询IP协议号")
    @ApiOperation("查询IP协议号")
    @PreAuthorize("@el.check('ntsIpPortal:list')")
    public ResponseEntity<Object> query(NtsIpPortalQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsIpPortalService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/ntsAllProtocols")
    @Log("查询IP协议号")
    @ApiOperation("查询IP协议号")
    @PreAuthorize("@el.check('ntsIpPortal:list')")
    public ResponseEntity<Object> ntsAllProtocols(){
        NtsIpPortalQueryCriteria criteria = new NtsIpPortalQueryCriteria();
        return new ResponseEntity<>(ntsIpPortalService.queryAll(criteria),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增IP协议号")
    @ApiOperation("新增IP协议号")
    @PreAuthorize("@el.check('ntsIpPortal:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsIpPortal resources){
        return new ResponseEntity<>(ntsIpPortalService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改IP协议号")
    @ApiOperation("修改IP协议号")
    @PreAuthorize("@el.check('ntsIpPortal:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsIpPortal resources){
        ntsIpPortalService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除IP协议号")
    @ApiOperation("删除IP协议号")
    @PreAuthorize("@el.check('ntsIpPortal:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsIpPortalService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}