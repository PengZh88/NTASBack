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
import me.zhengjie.algorithm.domain.NtsAlgorithm;
import me.zhengjie.algorithm.service.NtsAlgorithmService;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmQueryCriteria;
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
@Api(tags = "算法信息管理")
@RequestMapping("/api/ntsAlgorithm")
public class NtsAlgorithmController {

    private final NtsAlgorithmService ntsAlgorithmService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('ntsAlgorithm:list')")
    public void download(HttpServletResponse response, NtsAlgorithmQueryCriteria criteria) throws IOException {
        ntsAlgorithmService.download(ntsAlgorithmService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询算法信息")
    @ApiOperation("查询算法信息")
    @PreAuthorize("@el.check('ntsAlgorithm:list')")
    public ResponseEntity<Object> query(NtsAlgorithmQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(ntsAlgorithmService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增算法信息")
    @ApiOperation("新增算法信息")
    @PreAuthorize("@el.check('ntsAlgorithm:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody NtsAlgorithm resources){
        return new ResponseEntity<>(ntsAlgorithmService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改算法信息")
    @ApiOperation("修改算法信息")
    @PreAuthorize("@el.check('ntsAlgorithm:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody NtsAlgorithm resources){
        ntsAlgorithmService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除算法信息")
    @ApiOperation("删除算法信息")
    @PreAuthorize("@el.check('ntsAlgorithm:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        ntsAlgorithmService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("查看算法信息")
    @ApiOperation("查看算法信息")
    @GetMapping("/viewAlgorithm")
    @PreAuthorize("@el.check('ntsAlgorithm:list')")
    public ResponseEntity<Object> viewAlg(Long id) {
        return new ResponseEntity<>(ntsAlgorithmService.findById(id), HttpStatus.OK);
    }
}