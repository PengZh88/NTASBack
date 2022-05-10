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
package me.zhengjie.mongomysqltask.service.impl;

import cn.hutool.core.date.DateUtil;
import me.zhengjie.mongoapp.repository.dbprep.NtsMdbPrepRepository;
import me.zhengjie.mongomysqltask.domain.NtsMongoToMysqlTask;
import me.zhengjie.ntsprepdata.domain.NtsPrepFlowData;
import me.zhengjie.ntsprepdata.service.NtsPrepFlowDataService;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.mongomysqltask.repository.NtsMongoToMysqlTaskRepository;
import me.zhengjie.mongomysqltask.service.NtsMongoToMysqlTaskService;
import me.zhengjie.mongomysqltask.service.dto.NtsMongoToMysqlTaskDto;
import me.zhengjie.mongomysqltask.service.dto.NtsMongoToMysqlTaskQueryCriteria;
import me.zhengjie.mongomysqltask.service.mapstruct.NtsMongoToMysqlTaskMapper;
import org.bson.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Peng Zhan
 * @website https://el-admin.vip
 * @description 服务实现
 * @date 2021-02-20
 **/
@Service
@RequiredArgsConstructor
public class NtsMongoToMysqlTaskServiceImpl implements NtsMongoToMysqlTaskService {

    private final NtsMongoToMysqlTaskRepository ntsMongoToMysqlTaskRepository;
    private final NtsMdbPrepRepository ntsMdbPrepRepository;
    private final NtsMongoToMysqlTaskMapper ntsMongoToMysqlTaskMapper;
    private final NtsPrepFlowDataService ntsPrepFlowDataService;

    @Override
    public Map<String, Object> queryAll(NtsMongoToMysqlTaskQueryCriteria criteria, Pageable pageable) {
        Page<NtsMongoToMysqlTask> page = ntsMongoToMysqlTaskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(ntsMongoToMysqlTaskMapper::toDto));
    }

    @Override
    public List<NtsMongoToMysqlTaskDto> queryAll(NtsMongoToMysqlTaskQueryCriteria criteria) {
        return ntsMongoToMysqlTaskMapper.toDto(ntsMongoToMysqlTaskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Transactional
    public NtsMongoToMysqlTaskDto findById(Long id) {
        NtsMongoToMysqlTask ntsMongoToMysqlTask = ntsMongoToMysqlTaskRepository.findById(id).orElseGet(NtsMongoToMysqlTask::new);
        ValidationUtil.isNull(ntsMongoToMysqlTask.getId(), "NtsMongoToMysqlTask", "id", id);
        return ntsMongoToMysqlTaskMapper.toDto(ntsMongoToMysqlTask);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NtsMongoToMysqlTaskDto create(NtsMongoToMysqlTask resources) {
        if (resources.getStartNow().equals("N"))
            resources.setTaskStatus("unexecuted");
        else {
            resources.setTaskStatus("ongoing");
        }
        NtsMongoToMysqlTaskDto dto = ntsMongoToMysqlTaskMapper.toDto(ntsMongoToMysqlTaskRepository.save(resources));
        if (resources.getStartNow().equals("Y")) {
            this.startDataTransform(dto.getId());
        }
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(NtsMongoToMysqlTask resources) {
        NtsMongoToMysqlTask ntsMongoToMysqlTask = ntsMongoToMysqlTaskRepository.findById(resources.getId()).orElseGet(NtsMongoToMysqlTask::new);
        ValidationUtil.isNull(ntsMongoToMysqlTask.getId(), "NtsMongoToMysqlTask", "id", resources.getId());
        ntsMongoToMysqlTask.copy(resources);
        ntsMongoToMysqlTaskRepository.save(ntsMongoToMysqlTask);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            ntsMongoToMysqlTaskRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<NtsMongoToMysqlTaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (NtsMongoToMysqlTaskDto ntsMongoToMysqlTask : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("任务名称", ntsMongoToMysqlTask.getTaskName());
            map.put("MongoDBCollection", ntsMongoToMysqlTask.getCollectionName());
            map.put("网络协议", ntsMongoToMysqlTask.getFdProtocol());
            map.put("开始时间", ntsMongoToMysqlTask.getFdStartDate());
            map.put("结束时间", ntsMongoToMysqlTask.getFdEndDate());
            map.put("是否覆盖", ntsMongoToMysqlTask.getCanOverwrite());
            map.put("关联设备", ntsMongoToMysqlTask.getDeviceId());
            map.put("备注", ntsMongoToMysqlTask.getTaskRemark());
            map.put("任务状态", ntsMongoToMysqlTask.getTaskStatus());
            map.put("创建者", ntsMongoToMysqlTask.getCreateBy());
            map.put("更新者", ntsMongoToMysqlTask.getUpdatedBy());
            map.put("创建时间", ntsMongoToMysqlTask.getCreateTime());
            map.put("更新时间", ntsMongoToMysqlTask.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void updateTaskStatusById(Long id, String status) {
        NtsMongoToMysqlTask ntsMongoToMysqlTask = ntsMongoToMysqlTaskRepository.findById(id).orElseGet(NtsMongoToMysqlTask::new);
        ntsMongoToMysqlTask.setTaskStatus(status);
        ntsMongoToMysqlTaskRepository.save(ntsMongoToMysqlTask);
    }

    @Async
    @Override
    public void startDataTransform(Long id) {
        NtsMongoToMysqlTaskDto dto = this.findById(id);
        if ("Y".equals(dto.getCanOverwrite())) {
            // 覆盖导入，先删除之前的数据
            ntsPrepFlowDataService.deleteByFdProtocolAndFdDateTimeBetween(dto.getFdProtocol(), dto.getFdStartDate(), dto.getFdEndDate());
        }
        Iterator<Document> it = ntsMdbPrepRepository.findCollectionQueryData(dto.getCollectionName(), dto.getFdStartDate(), dto.getFdEndDate(), dto.getFdProtocol());
        List<NtsPrepFlowData> list = new ArrayList<>();
        while (it.hasNext()) {
            Document document = it.next();
            NtsPrepFlowData data = new NtsPrepFlowData();
            data.setFdDateTime(DateUtil.parseDateTime(document.getString("fdatetime")).toTimestamp());
            data.setFdProtocol(document.getString("protocol"));
            data.setFdValue(new BigDecimal(document.getDouble("fvalue")));
            data.setFdWeight("KB");
            data.setDeviceId(dto.getDeviceId());
            data.setTaskId(dto.getId());
            list.add(data);
        }
        NtsMongoToMysqlTask task = ntsMongoToMysqlTaskRepository.findById(id).orElseGet(NtsMongoToMysqlTask::new);
        String state = "succeeded";
        try {
            ntsPrepFlowDataService.saveAll(list);
        } catch (Exception e) {
            state = "failed";
        } finally {
            task.setTaskStatus(state);
            this.update(task);
        }
    }
}