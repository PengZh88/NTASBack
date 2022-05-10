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
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-11-12
**/
@Data
public class NtsAnomalyDetectionPersistenceDataDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 检测任务主键 */
    private Long adTaskId;

    /** 待检测数据 */
    private String fdValues;

    /** 待检测标准化数据 */
    private String fdNormValues;
}