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

import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionPersistenceData;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.anomalydetection.repository.NtsAnomalyDetectionPersistenceDataRepository;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionPersistenceDataService;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionPersistenceDataDto;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionPersistenceDataQueryCriteria;
import me.zhengjie.anomalydetection.service.mapstruct.NtsAnomalyDetectionPersistenceDataMapper;
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
* @date 2021-11-12
**/
@Service
@RequiredArgsConstructor
public class NtsAnomalyDetectionPersistenceDataServiceImpl implements NtsAnomalyDetectionPersistenceDataService {

    private final NtsAnomalyDetectionPersistenceDataRepository ntsAnomalyDetectionPersistenceDataRepository;
    private final NtsAnomalyDetectionPersistenceDataMapper ntsAnomalyDetectionPersistenceDataMapper;

    @Override
    public Map<String,Object> queryAll(NtsAnomalyDetectionPersistenceDataQueryCriteria criteria, Pageable pageable){
        Page<NtsAnomalyDetectionPersistenceData> page = ntsAnomalyDetectionPersistenceDataRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsAnomalyDetectionPersistenceDataMapper::toDto));
    }

    @Override
    public List<NtsAnomalyDetectionPersistenceDataDto> queryAll(NtsAnomalyDetectionPersistenceDataQueryCriteria criteria){
        return ntsAnomalyDetectionPersistenceDataMapper.toDto(ntsAnomalyDetectionPersistenceDataRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsAnomalyDetectionPersistenceDataDto findById(Long id) {
        NtsAnomalyDetectionPersistenceData ntsAnomalyDetectionPersistenceData = ntsAnomalyDetectionPersistenceDataRepository.findById(id).orElseGet(NtsAnomalyDetectionPersistenceData::new);
        ValidationUtil.isNull(ntsAnomalyDetectionPersistenceData.getId(),"NtsAnomalyDetectionPersistenceData","id",id);
        return ntsAnomalyDetectionPersistenceDataMapper.toDto(ntsAnomalyDetectionPersistenceData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsAnomalyDetectionPersistenceDataDto create(NtsAnomalyDetectionPersistenceData resources) {
        return ntsAnomalyDetectionPersistenceDataMapper.toDto(ntsAnomalyDetectionPersistenceDataRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsAnomalyDetectionPersistenceData resources) {
        NtsAnomalyDetectionPersistenceData ntsAnomalyDetectionPersistenceData = ntsAnomalyDetectionPersistenceDataRepository.findById(resources.getId()).orElseGet(NtsAnomalyDetectionPersistenceData::new);
        ValidationUtil.isNull( ntsAnomalyDetectionPersistenceData.getId(),"NtsAnomalyDetectionPersistenceData","id",resources.getId());
        ntsAnomalyDetectionPersistenceData.copy(resources);
        ntsAnomalyDetectionPersistenceDataRepository.save(ntsAnomalyDetectionPersistenceData);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsAnomalyDetectionPersistenceDataRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsAnomalyDetectionPersistenceDataDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsAnomalyDetectionPersistenceDataDto ntsAnomalyDetectionPersistenceData : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("检测任务主键", ntsAnomalyDetectionPersistenceData.getAdTaskId());
            map.put("待检测数据", ntsAnomalyDetectionPersistenceData.getFdValues());
            map.put("待检测标准化数据", ntsAnomalyDetectionPersistenceData.getFdNormValues());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public NtsAnomalyDetectionPersistenceDataDto findByAdTaskId(Long taskId) {
        return ntsAnomalyDetectionPersistenceDataMapper.toDto(ntsAnomalyDetectionPersistenceDataRepository.findByAdTaskId(taskId));
    }
}