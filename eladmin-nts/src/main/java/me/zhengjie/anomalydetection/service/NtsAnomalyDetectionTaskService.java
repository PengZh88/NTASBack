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
package me.zhengjie.anomalydetection.service;

import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionTask;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionTaskDto;
import me.zhengjie.anomalydetection.service.dto.NtsAnomalyDetectionTaskQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author Peng Zhan
* @date 2021-03-10
**/
public interface NtsAnomalyDetectionTaskService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(NtsAnomalyDetectionTaskQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<NtsAnomalyDetectionTaskDto>
    */
    List<NtsAnomalyDetectionTaskDto> queryAll(NtsAnomalyDetectionTaskQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return NtsAnomalyDetectionTaskDto
     */
    NtsAnomalyDetectionTaskDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return NtsAnomalyDetectionTaskDto
    */
    NtsAnomalyDetectionTaskDto create(NtsAnomalyDetectionTask resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(NtsAnomalyDetectionTask resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<NtsAnomalyDetectionTaskDto> all, HttpServletResponse response) throws IOException;

    /**
     * 更新任务状态为启动，同时设置启动时间
     * @param id
     */
    void updateTaskStart(Long id);

    /**
     * 更新任务结束时间
     * @param id
     */
    void updateTaskEnd(Long id);

    /**
     * 启动异常检测任务
     * @param id
     */
    void startAnomalyTask(Long id);

    /**
     * 获取检测任务原始数据的起始时间
     * @param id
     * @return
     */
    Timestamp findNtsStartById(Long id);
}