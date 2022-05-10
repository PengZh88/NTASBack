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
import javax.persistence.Entity;
import javax.persistence.Table;

import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersDto;
import me.zhengjie.base.BaseEntity;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;
import java.util.List;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-03-10
**/
@Entity
@Data
@Table(name="nts_anomaly_detection_task")
public class NtsAnomalyDetectionTask extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "task_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @Column(name = "device_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "设备")
    private Long deviceId;

    @Column(name = "nts_protocol",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "网络协议")
    private String ntsProtocol;

    @Column(name = "nts_start",nullable = false)
    @NotNull
    @ApiModelProperty(value = "流量开始时间")
    private Timestamp ntsStart;

    @Column(name = "nts_end",nullable = false)
    @NotNull
    @ApiModelProperty(value = "流量结束时间")
    private Timestamp ntsEnd;

    @Column(name = "algorithm_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "检测算法")
    private Long algorithmId;

    @Column(name = "ana_dimension",nullable = false)
    @NotNull
    @ApiModelProperty(value = "分析维度")
    private String anaDimension;

    @Column(name = "fine_grained",nullable = false)
    @NotNull
    @ApiModelProperty(value = "精细粒度")
    private String fineGrained;

    @Column(name = "window_size",nullable = false)
    @NotNull
    @ApiModelProperty(value = "窗口大小")
    private Integer windowSize;

    @Column(name = "step_size",nullable = false)
    @NotNull
    @ApiModelProperty(value = "步长")
    private Integer stepSize;

    @Column(name = "drill_style")
    @ApiModelProperty(value = "是否下钻检测")
    private String drillStyle;

    @Column(name = "start_now")
    @ApiModelProperty(value = "任务是否启动")
    private String startNow;


    @Column(name = "task_start_time")
    @ApiModelProperty(value = "任务启动时间")
    private Timestamp taskStartTime;

    @Column(name = "task_end_time")
    @ApiModelProperty(value = "任务结束时间")
    private Timestamp taskEndTime;

    @Transient
    private List<NtsAlgorithmParametersDto> algParamDatas;

    public void copy(NtsAnomalyDetectionTask source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}