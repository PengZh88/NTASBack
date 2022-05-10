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

import me.zhengjie.algorithm.domain.NtsAlgorithm;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.algorithm.repository.NtsAlgorithmRepository;
import me.zhengjie.algorithm.service.NtsAlgorithmService;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmDto;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmQueryCriteria;
import me.zhengjie.algorithm.service.mapstruct.NtsAlgorithmMapper;
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
public class NtsAlgorithmServiceImpl implements NtsAlgorithmService {

    private final NtsAlgorithmRepository ntsAlgorithmRepository;
    private final NtsAlgorithmMapper ntsAlgorithmMapper;

    @Override
    public Map<String,Object> queryAll(NtsAlgorithmQueryCriteria criteria, Pageable pageable){
        Page<NtsAlgorithm> page = ntsAlgorithmRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsAlgorithmMapper::toDto));
    }

    @Override
    public List<NtsAlgorithmDto> queryAll(NtsAlgorithmQueryCriteria criteria){
        return ntsAlgorithmMapper.toDto(ntsAlgorithmRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsAlgorithmDto findById(Long id) {
        NtsAlgorithm ntsAlgorithm = ntsAlgorithmRepository.findById(id).orElseGet(NtsAlgorithm::new);
        ValidationUtil.isNull(ntsAlgorithm.getId(),"NtsAlgorithm","id",id);
        return ntsAlgorithmMapper.toDto(ntsAlgorithm);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsAlgorithmDto create(NtsAlgorithm resources) {
        return ntsAlgorithmMapper.toDto(ntsAlgorithmRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsAlgorithm resources) {
        NtsAlgorithm ntsAlgorithm = ntsAlgorithmRepository.findById(resources.getId()).orElseGet(NtsAlgorithm::new);
        ValidationUtil.isNull( ntsAlgorithm.getId(),"NtsAlgorithm","id",resources.getId());
        ntsAlgorithm.copy(resources);
        ntsAlgorithmRepository.save(ntsAlgorithm);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsAlgorithmRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsAlgorithmDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsAlgorithmDto ntsAlgorithm : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("算法名称", ntsAlgorithm.getAlgName());
            map.put("算法简要描述", ntsAlgorithm.getAlgDesc());
            map.put("相关论文", ntsAlgorithm.getPaperName());
            map.put("论文发表年", ntsAlgorithm.getPaperYear());
            map.put("期刊（会议）名称", ntsAlgorithm.getPaperJcname());
            map.put("论文类型", ntsAlgorithm.getPaperType());
            map.put("收录情况", ntsAlgorithm.getPaperInclude());
            map.put("论文作者", ntsAlgorithm.getPaperAuthors());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}