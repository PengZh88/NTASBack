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

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.algorithm.factory.AlgorithmCreateFactory;
import me.zhengjie.algorithm.service.NtsAlgorithmImplService;
import me.zhengjie.algorithm.service.NtsAlgorithmService;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmDto;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionParameters;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionPersistenceData;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionResults;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionTask;
import me.zhengjie.anomalydetection.repository.NtsAnomalyDetectionTaskRepository;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionParametersService;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionPersistenceDataService;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionResultsService;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionTaskService;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionPersistenceDataDto;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionTaskDto;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionTaskQueryCriteria;
import me.zhengjie.anomalydetection.service.mapstruct.NtsAnomalyDetectionTaskMapper;
import me.zhengjie.ntsprepdata.service.NtsPrepFlowDataService;
import me.zhengjie.ntsprepdata.service.dto.NtsPersistenceAnomalyI;
import me.zhengjie.utils.*;
import net.seninp.jmotif.sax.discord.DiscordRecord;
import net.seninp.jmotif.sax.discord.DiscordRecords;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Peng Zhan
 * @website https://el-admin.vip
 * @description ????????????
 * @date 2021-03-10
 **/
@Service
@RequiredArgsConstructor
public class NtsAnomalyDetectionTaskServiceImpl implements NtsAnomalyDetectionTaskService {

    private final NtsAnomalyDetectionTaskRepository ntsAnomalyDetectionTaskRepository;
    private final NtsAnomalyDetectionTaskMapper ntsAnomalyDetectionTaskMapper;
    private final NtsPrepFlowDataService ntsPrepFlowDataService;
    private final NtsAnomalyDetectionPersistenceDataService ntsAnomalyDetectionPersistenceDataService;
    private final NtsAlgorithmService ntsAlgorithmService;
    private final NtsAnomalyDetectionParametersService ntsAnomalyDetectionParametersService;
    private final NtsAnomalyDetectionResultsService ntsAnomalyDetectionResultsService;

    @Override
    public Map<String, Object> queryAll(NtsAnomalyDetectionTaskQueryCriteria criteria, Pageable pageable) {
        Page<NtsAnomalyDetectionTask> page = ntsAnomalyDetectionTaskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(ntsAnomalyDetectionTaskMapper::toDto));
    }

    @Override
    public List<NtsAnomalyDetectionTaskDto> queryAll(NtsAnomalyDetectionTaskQueryCriteria criteria) {
        return ntsAnomalyDetectionTaskMapper.toDto(ntsAnomalyDetectionTaskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsAnomalyDetectionTaskDto findById(Long id) {
        NtsAnomalyDetectionTask ntsAnomalyDetectionTask = ntsAnomalyDetectionTaskRepository.findById(id).orElseGet(NtsAnomalyDetectionTask::new);
        ValidationUtil.isNull(ntsAnomalyDetectionTask.getId(), "NtsAnomalyDetectionTask", "id", id);
        return ntsAnomalyDetectionTaskMapper.toDto(ntsAnomalyDetectionTask);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsAnomalyDetectionTaskDto create(NtsAnomalyDetectionTask resources) {
        return ntsAnomalyDetectionTaskMapper.toDto(ntsAnomalyDetectionTaskRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsAnomalyDetectionTask resources) {
        NtsAnomalyDetectionTask ntsAnomalyDetectionTask = ntsAnomalyDetectionTaskRepository.findById(resources.getId()).orElseGet(NtsAnomalyDetectionTask::new);
        ValidationUtil.isNull(ntsAnomalyDetectionTask.getId(), "NtsAnomalyDetectionTask", "id", resources.getId());
        ntsAnomalyDetectionTask.copy(resources);
        ntsAnomalyDetectionTaskRepository.save(ntsAnomalyDetectionTask);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsAnomalyDetectionTaskRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsAnomalyDetectionTaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsAnomalyDetectionTaskDto ntsAnomalyDetectionTask : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("????????????", ntsAnomalyDetectionTask.getTaskName());
            map.put("??????", ntsAnomalyDetectionTask.getDeviceId());
            map.put("????????????", ntsAnomalyDetectionTask.getNtsProtocol());
            map.put("??????????????????", ntsAnomalyDetectionTask.getNtsStart());
            map.put("??????????????????", ntsAnomalyDetectionTask.getNtsEnd());
            map.put("????????????", ntsAnomalyDetectionTask.getAlgorithmId());
            map.put("????????????", ntsAnomalyDetectionTask.getWindowSize());
            map.put("??????", ntsAnomalyDetectionTask.getStepSize());
            map.put("??????????????????", ntsAnomalyDetectionTask.getDrillStyle());
            map.put("?????????", ntsAnomalyDetectionTask.getCreateBy());
            map.put("?????????", ntsAnomalyDetectionTask.getUpdatedBy());
            map.put("????????????", ntsAnomalyDetectionTask.getCreateTime());
            map.put("????????????", ntsAnomalyDetectionTask.getUpdateTime());
            map.put("??????????????????", ntsAnomalyDetectionTask.getStartNow());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void updateTaskStart(Long id) {
        NtsAnomalyDetectionTask task = ntsAnomalyDetectionTaskRepository.findById(id).orElseGet(NtsAnomalyDetectionTask::new);
        task.setStartNow("Y");
        task.setTaskStartTime(DateUtil.parseDateTime(DateUtil.now()).toTimestamp());
        ntsAnomalyDetectionTaskRepository.save(task);
    }

    @Override
    public void updateTaskEnd(Long id) {
        NtsAnomalyDetectionTask task = ntsAnomalyDetectionTaskRepository.findById(id).orElseGet(NtsAnomalyDetectionTask::new);
        task.setTaskEndTime(DateUtil.parseDateTime(DateUtil.now()).toTimestamp());
        ntsAnomalyDetectionTaskRepository.save(task);
    }


    @Async
    @Override
    public void startAnomalyTask(Long id) {
        // ?????????????????????????????????
        updateTaskStart(id);

        // ????????????????????????
        NtsAnomalyDetectionTask task = ntsAnomalyDetectionTaskRepository.findById(id).orElseGet(NtsAnomalyDetectionTask::new);
        Long deviceId = task.getDeviceId();
        String protocol = task.getNtsProtocol();
        List<NtsPersistenceAnomalyI> paDtos = ntsPrepFlowDataService.findAnomalyPreData(task.getAnaDimension(), task.getFineGrained(), deviceId, protocol, task.getNtsStart(), task.getNtsEnd());
        NtsAnomalyDetectionPersistenceDataDto pdDto = null;
        double[] da = null;
        double[] normArray = null;
        if (paDtos != null && !paDtos.isEmpty()) {
            da = new double[paDtos.size()];
            for (int i = 0, size = paDtos.size(); i < size; i++) {
                NtsPersistenceAnomalyI dto = paDtos.get(i);
                da[i] = dto.getPaSumValue().doubleValue();
            }
            String values = ArrayUtil.join(da, ",");
            normArray = AdMathUtils.zNormalize(da);
            String normValues = ArrayUtil.join(normArray, ",");
            NtsAnomalyDetectionPersistenceData persistenceData = new NtsAnomalyDetectionPersistenceData();
            persistenceData.setAdTaskId(id);
            persistenceData.setFdValues(values);
            persistenceData.setFdNormValues(normValues);
            pdDto = ntsAnomalyDetectionPersistenceDataService.create(persistenceData);
        }

        if (pdDto != null) {
            // ????????????????????????
            NtsAlgorithmDto algorithmDto = ntsAlgorithmService.findById(task.getAlgorithmId());
            List<NtsAnomalyDetectionParameters> parametersDtos = ntsAnomalyDetectionParametersService.queryByTaskId(task.getId());
            NtsAlgorithmImplService algService = AlgorithmCreateFactory.createAlgorithm(algorithmDto.getAlgName());
            DiscordRecords discordRecords = algService.series2AnomaliesRecords(normArray, task.getWindowSize(), parametersDtos);
            System.out.println("????????????????????? \n" + discordRecords);
            for (int i = 0; i < discordRecords.getSize(); ++i) {
                DiscordRecord record = (DiscordRecord) discordRecords.get(i);
                NtsAnomalyDetectionResults result = new NtsAnomalyDetectionResults();
                result.setAdTaskId(task.getId());
                result.setAsPosition(record.getPosition());
                result.setAsLength(record.getLength());
                ntsAnomalyDetectionResultsService.create(result);
            }
        }
        // ?????????????????????????????????
        updateTaskEnd(id);
        System.out.println("??????????????????????????????!");
    }

    @Override
    public Timestamp findNtsStartById(Long id) {
        return ntsAnomalyDetectionTaskRepository.findById(id).orElseGet(NtsAnomalyDetectionTask::new).getNtsStart();
    }
}