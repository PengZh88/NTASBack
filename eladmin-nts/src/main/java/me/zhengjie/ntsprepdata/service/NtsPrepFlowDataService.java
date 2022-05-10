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
package me.zhengjie.ntsprepdata.service;

import me.zhengjie.ntsprepdata.domain.NtsPrepFlowData;
import me.zhengjie.ntsprepdata.service.dto.NtsPersistenceAnomalyI;
import me.zhengjie.ntsprepdata.service.dto.NtsPrepFlowDataDto;
import me.zhengjie.ntsprepdata.service.dto.NtsPrepFlowDataQueryCriteria;
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
* @date 2021-02-27
**/
public interface NtsPrepFlowDataService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(NtsPrepFlowDataQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<NtsPrepFlowDataDto>
    */
    List<NtsPrepFlowDataDto> queryAll(NtsPrepFlowDataQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return NtsPrepFlowDataDto
     */
    NtsPrepFlowDataDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return NtsPrepFlowDataDto
    */
    NtsPrepFlowDataDto create(NtsPrepFlowData resources);

    /**
     * 批量创建
     * @param resources
     */
    void saveAll(List<NtsPrepFlowData> resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(NtsPrepFlowData resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
     * 按照范围查询
     * @param protocol
     * @param start
     * @param end
     * @return
     */
    List<NtsPrepFlowData> findByFdProtocolAndFdDateTimeBetween(String protocol, Timestamp start, Timestamp end);

    /**
     * 按照范围删除
     * @param protocol
     * @param start
     * @param end
     */
    void deleteByFdProtocolAndFdDateTimeBetween(String protocol, Timestamp start, Timestamp end);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<NtsPrepFlowDataDto> all, HttpServletResponse response) throws IOException;


    List<NtsPersistenceAnomalyI> findAnomalyPreData(String anaDimension, String fineGrained, Long deviceId, String protocol, Timestamp startTime, Timestamp endTime);
}