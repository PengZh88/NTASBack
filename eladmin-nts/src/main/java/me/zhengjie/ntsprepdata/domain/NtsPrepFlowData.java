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
package me.zhengjie.ntsprepdata.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-02-27
**/
@Entity
@Data
@Table(name="nts_prep_flow_data")
public class NtsPrepFlowData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "fd_date_time",nullable = false)
    @NotNull
    @ApiModelProperty(value = "流量产生时间")
    private Timestamp fdDateTime;

    @Column(name = "fd_value",nullable = false)
    @NotNull
    @ApiModelProperty(value = "流量值")
    private BigDecimal fdValue;

    @Column(name = "fd_protocol",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "协议")
    private String fdProtocol;

    @Column(name = "fd_weight",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "流量单位")
    private String fdWeight;

    @Column(name = "device_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "关联设备主键")
    private Long deviceId;

    @Column(name = "task_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "关联同步任务主键")
    private Long taskId;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "数据入库时间")
    private Timestamp createTime;

    @Transient
    private Timestamp paDate;

    @Transient
    private BigDecimal paSumValue;

    public void copy(NtsPrepFlowData source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}