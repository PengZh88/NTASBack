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
* @date 2021-11-20
**/
@Data
public class NtsAnomalyDetectionResultsDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 异常检测任务 */
    private Long adTaskId;

    /** 异常位置 */
    private Integer asPosition;

    /** 异常长度 */
    private Integer asLength;
}