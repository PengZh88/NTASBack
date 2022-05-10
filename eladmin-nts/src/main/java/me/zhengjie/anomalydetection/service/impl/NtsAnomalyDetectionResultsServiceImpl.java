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
package me.zhengjie.anomalydetection.service.impl;

import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionResults;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.anomalydetection.repository.NtsAnomalyDetectionResultsRepository;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionResultsService;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionResultsDto;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionResultsQueryCriteria;
import me.zhengjie.anomalydetection.service.mapstruct.NtsAnomalyDetectionResultsMapper;
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
* @date 2021-11-20
**/
@Service
@RequiredArgsConstructor
public class NtsAnomalyDetectionResultsServiceImpl implements NtsAnomalyDetectionResultsService {

    private final NtsAnomalyDetectionResultsRepository ntsAnomalyDetectionResultsRepository;
    private final NtsAnomalyDetectionResultsMapper ntsAnomalyDetectionResultsMapper;

    @Override
    public Map<String,Object> queryAll(NtsAnomalyDetectionResultsQueryCriteria criteria, Pageable pageable){
        Page<NtsAnomalyDetectionResults> page = ntsAnomalyDetectionResultsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsAnomalyDetectionResultsMapper::toDto));
    }

    @Override
    public List<NtsAnomalyDetectionResultsDto> queryAll(NtsAnomalyDetectionResultsQueryCriteria criteria){
        return ntsAnomalyDetectionResultsMapper.toDto(ntsAnomalyDetectionResultsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsAnomalyDetectionResultsDto findById(Long id) {
        NtsAnomalyDetectionResults ntsAnomalyDetectionResults = ntsAnomalyDetectionResultsRepository.findById(id).orElseGet(NtsAnomalyDetectionResults::new);
        ValidationUtil.isNull(ntsAnomalyDetectionResults.getId(),"NtsAnomalyDetectionResults","id",id);
        return ntsAnomalyDetectionResultsMapper.toDto(ntsAnomalyDetectionResults);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsAnomalyDetectionResultsDto create(NtsAnomalyDetectionResults resources) {
        return ntsAnomalyDetectionResultsMapper.toDto(ntsAnomalyDetectionResultsRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsAnomalyDetectionResults resources) {
        NtsAnomalyDetectionResults ntsAnomalyDetectionResults = ntsAnomalyDetectionResultsRepository.findById(resources.getId()).orElseGet(NtsAnomalyDetectionResults::new);
        ValidationUtil.isNull( ntsAnomalyDetectionResults.getId(),"NtsAnomalyDetectionResults","id",resources.getId());
        ntsAnomalyDetectionResults.copy(resources);
        ntsAnomalyDetectionResultsRepository.save(ntsAnomalyDetectionResults);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsAnomalyDetectionResultsRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsAnomalyDetectionResultsDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsAnomalyDetectionResultsDto ntsAnomalyDetectionResults : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("异常检测任务", ntsAnomalyDetectionResults.getAdTaskId());
            map.put("异常位置", ntsAnomalyDetectionResults.getAsPosition());
            map.put("异常长度", ntsAnomalyDetectionResults.getAsLength());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}