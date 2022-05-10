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
package me.zhengjie.algorithm.service.impl;

import me.zhengjie.algorithm.domain.NtsAlgorithmParameters;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.algorithm.repository.NtsAlgorithmParametersRepository;
import me.zhengjie.algorithm.service.NtsAlgorithmParametersService;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersDto;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersQueryCriteria;
import me.zhengjie.algorithm.service.mapstruct.NtsAlgorithmParametersMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author Peng Zhan
* @date 2020-12-26
**/
@Service
@RequiredArgsConstructor
public class NtsAlgorithmParametersServiceImpl implements NtsAlgorithmParametersService {

    private final NtsAlgorithmParametersRepository ntsAlgorithmParametersRepository;
    private final NtsAlgorithmParametersMapper ntsAlgorithmParametersMapper;

    @Override
    public Map<String,Object> queryAll(NtsAlgorithmParametersQueryCriteria criteria, Pageable pageable){
        Page<NtsAlgorithmParameters> page = ntsAlgorithmParametersRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsAlgorithmParametersMapper::toDto));
    }

    @Override
    public List<NtsAlgorithmParametersDto> queryAll(NtsAlgorithmParametersQueryCriteria criteria){
        return ntsAlgorithmParametersMapper.toDto(ntsAlgorithmParametersRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsAlgorithmParametersDto findById(Long id) {
        NtsAlgorithmParameters ntsAlgorithmParameters = ntsAlgorithmParametersRepository.findById(id).orElseGet(NtsAlgorithmParameters::new);
        ValidationUtil.isNull(ntsAlgorithmParameters.getId(),"NtsAlgorithmParameters","id",id);
        return ntsAlgorithmParametersMapper.toDto(ntsAlgorithmParameters);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsAlgorithmParametersDto create(NtsAlgorithmParameters resources) {
        return ntsAlgorithmParametersMapper.toDto(ntsAlgorithmParametersRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsAlgorithmParameters resources) {
        NtsAlgorithmParameters ntsAlgorithmParameters = ntsAlgorithmParametersRepository.findById(resources.getId()).orElseGet(NtsAlgorithmParameters::new);
        ValidationUtil.isNull( ntsAlgorithmParameters.getId(),"NtsAlgorithmParameters","id",resources.getId());
        ntsAlgorithmParameters.copy(resources);
        ntsAlgorithmParametersRepository.save(ntsAlgorithmParameters);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsAlgorithmParametersRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsAlgorithmParametersDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsAlgorithmParametersDto ntsAlgorithmParameters : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("参数名称", ntsAlgorithmParameters.getParName());
            map.put("参数值", ntsAlgorithmParameters.getParValue());
            map.put("备注", ntsAlgorithmParameters.getParRemark());
            map.put("关联算法", ntsAlgorithmParameters.getAlgId());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}