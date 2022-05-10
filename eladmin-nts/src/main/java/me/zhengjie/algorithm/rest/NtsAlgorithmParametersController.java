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
package me.zhengjie.algorithm.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.algorithm.domain.NtsAlgorithmParameters;
import me.zhengjie.algorithm.service.NtsAlgorithmParametersService;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersQueryCriteria;
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
* @date 2020-12-26
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "算法参数信息管理")
@RequestMapping("/api/ntsAlgorithmParameters")
public class NtsAlgorithmParametersController {

    private final NtsAlgorithmParametersService ntsAlgorithmParametersService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsAlgorithmParameters:list')")
    public void download(HttpServletResponse response, NtsAlgorithmParametersQueryCriteria criteria) throws IOException {
        ntsAlgorithmParametersService.download(ntsAlgorithmParametersService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询算法参数信息")
    @ApiOperation("查询算法参数信息")
    @PreAuthorize("@el.check('ntsAlgorithmParameters:list')")
    public ResponseEntity<Object> query(NtsAlgorithmParametersQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsAlgorithmParametersService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增算法参数信息")
    @ApiOperation("新增算法参数信息")
    @PreAuthorize("@el.check('ntsAlgorithmParameters:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsAlgorithmParameters resources){
        return new ResponseEntity<>(ntsAlgorithmParametersService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改算法参数信息")
    @ApiOperation("修改算法参数信息")
    @PreAuthorize("@el.check('ntsAlgorithmParameters:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsAlgorithmParameters resources){
        ntsAlgorithmParametersService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除算法参数信息")
    @ApiOperation("删除算法参数信息")
    @PreAuthorize("@el.check('ntsAlgorithmParameters:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsAlgorithmParametersService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}