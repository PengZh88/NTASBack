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
package me.zhengjie.ntsprepdata.service.impl;

import me.zhengjie.ntsprepdata.domain.NtsPrepFlowData;
import me.zhengjie.ntsprepdata.repository.PreFlowDataAnomalyRepository;
import me.zhengjie.ntsprepdata.service.dto.NtsPersistenceAnomalyI;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.ntsprepdata.repository.NtsPrepFlowDataRepository;
import me.zhengjie.ntsprepdata.service.NtsPrepFlowDataService;
import me.zhengjie.ntsprepdata.service.dto.NtsPrepFlowDataDto;
import me.zhengjie.ntsprepdata.service.dto.NtsPrepFlowDataQueryCriteria;
import me.zhengjie.ntsprepdata.service.mapstruct.NtsPrepFlowDataMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.sql.Timestamp;
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
* @date 2021-02-27
**/
@Service
@RequiredArgsConstructor
public class NtsPrepFlowDataServiceImpl implements NtsPrepFlowDataService {

    private final NtsPrepFlowDataRepository ntsPrepFlowDataRepository;
    private final PreFlowDataAnomalyRepository preFlowDataAnomalyRepository;
    private final NtsPrepFlowDataMapper ntsPrepFlowDataMapper;

    @Override
    public Map<String,Object> queryAll(NtsPrepFlowDataQueryCriteria criteria, Pageable pageable){
        Page<NtsPrepFlowData> page = ntsPrepFlowDataRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(ntsPrepFlowDataMapper::toDto));
    }

    @Override
    public List<NtsPrepFlowDataDto> queryAll(NtsPrepFlowDataQueryCriteria criteria){
        return ntsPrepFlowDataMapper.toDto(ntsPrepFlowDataRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsPrepFlowDataDto findById(Long id) {
        NtsPrepFlowData ntsPrepFlowData = ntsPrepFlowDataRepository.findById(id).orElseGet(NtsPrepFlowData::new);
        ValidationUtil.isNull(ntsPrepFlowData.getId(),"NtsPrepFlowData","id",id);
        return ntsPrepFlowDataMapper.toDto(ntsPrepFlowData);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsPrepFlowDataDto create(NtsPrepFlowData resources) {
        return ntsPrepFlowDataMapper.toDto(ntsPrepFlowDataRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<NtsPrepFlowData> resources) {
        ntsPrepFlowDataRepository.saveAll(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsPrepFlowData resources) {
        NtsPrepFlowData ntsPrepFlowData = ntsPrepFlowDataRepository.findById(resources.getId()).orElseGet(NtsPrepFlowData::new);
        ValidationUtil.isNull( ntsPrepFlowData.getId(),"NtsPrepFlowData","id",resources.getId());
        ntsPrepFlowData.copy(resources);
        ntsPrepFlowDataRepository.save(ntsPrepFlowData);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsPrepFlowDataRepository.deleteById(id);
        }
    }

    @Override
    public List<NtsPrepFlowData> findByFdProtocolAndFdDateTimeBetween(String protocol, Timestamp start, Timestamp end) {
        return ntsPrepFlowDataRepository.findByFdProtocolAndFdDateTimeBetween(protocol, start, end);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByFdProtocolAndFdDateTimeBetween(String protocol, Timestamp start, Timestamp end) {
        ntsPrepFlowDataRepository.deleteByFdProtocolAndFdDateTimeBetween(protocol, start, end);
    }

    @Override
    public void download(List<NtsPrepFlowDataDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsPrepFlowDataDto ntsPrepFlowData : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("流量产生时间", ntsPrepFlowData.getFdDateTime());
            map.put("流量值", ntsPrepFlowData.getFdValue());
            map.put("协议", ntsPrepFlowData.getFdProtocol());
            map.put("流量单位", ntsPrepFlowData.getFdWeight());
            map.put("关联设备主键", ntsPrepFlowData.getDeviceId());
            map.put("关联同步任务主键", ntsPrepFlowData.getTaskId());
            map.put("数据入库时间", ntsPrepFlowData.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<NtsPersistenceAnomalyI> findAnomalyPreData(String anaDimension, String fineGrained, Long deviceId, String protocol, Timestamp startTime, Timestamp endTime) {
        List<NtsPersistenceAnomalyI> dtos = null;
        if ("H".equalsIgnoreCase(fineGrained)) {
            dtos = preFlowDataAnomalyRepository.findNtsPrepDataByHour(deviceId, protocol, startTime, endTime);
        } else if ("M".equalsIgnoreCase(fineGrained)) {
            dtos = preFlowDataAnomalyRepository.findNtsPrepDataByMinute(deviceId, protocol, startTime, endTime);
        } else if ("S".equalsIgnoreCase(fineGrained)) {
            dtos = preFlowDataAnomalyRepository.findNtsPrepDataBySecond(deviceId, protocol, startTime, endTime);
        } else {
            dtos = new ArrayList<NtsPersistenceAnomalyI>();
        }
        return dtos;
    }
}