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
package me.zhengjie.anomalydetection.service.dto;

import lombok.Data;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersDto;

import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-03-10
**/
@Data
public class NtsAnomalyDetectionTaskDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 任务名称 */
    private String taskName;

    /** 设备 */
    private Long deviceId;

    /** 网络协议 */
    private String ntsProtocol;

    /** 流量开始时间 */
    private Timestamp ntsStart;

    /** 流量结束时间 */
    private Timestamp ntsEnd;

    /** 检测算法 */
    private Long algorithmId;

    /** 分析维度 */
    private String anaDimension;

    /** 精细粒度 */
    private String fineGrained;

    /** 窗口大小 */
    private Integer windowSize;

    /** 步长 */
    private Integer stepSize;

    /** 是否下钻检测 */
    private String drillStyle;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updatedBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 任务是否启动 */
    private String startNow;

    /** 任务启动时间 */
    private Timestamp taskStartTime;

    /** 任务结束时间 */
    private Timestamp taskEndTime;

    /** 算法参数 */
    private List<NtsAlgorithmParametersDto> algParamDatas;
}