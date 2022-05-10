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
package me.zhengjie.anomalydetection.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-11-12
**/
@Entity
@Data
@Table(name="nts_anomaly_detection_persistence_data")
public class NtsAnomalyDetectionPersistenceData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "ad_task_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "检测任务主键")
    private Long adTaskId;

    @Column(name = "fd_values")
    @ApiModelProperty(value = "待检测数据")
    private String fdValues;

    @Column(name = "fd_norm_values")
    @ApiModelProperty(value = "待检测标准化数据")
    private String fdNormValues;

    public void copy(NtsAnomalyDetectionPersistenceData source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}