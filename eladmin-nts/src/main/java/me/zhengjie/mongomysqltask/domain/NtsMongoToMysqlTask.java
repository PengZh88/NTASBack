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
package me.zhengjie.mongomysqltask.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.zhengjie.base.BaseEntity;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-02-20
**/
@Entity
@Data
@Table(name="nts_mongo_to_mysql_task")
public class NtsMongoToMysqlTask extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "task_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @Column(name = "collection_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "MongoDBCollection")
    private String collectionName;

    @Column(name = "fd_protocol",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "网络协议")
    private String fdProtocol;

    @Column(name = "fd_start_date",nullable = false)
    @NotNull
    @ApiModelProperty(value = "开始时间")
    private Timestamp fdStartDate;

    @Column(name = "fd_end_date",nullable = false)
    @NotNull
    @ApiModelProperty(value = "结束时间")
    private Timestamp fdEndDate;

    @Column(name = "can_overwrite",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "是否覆盖")
    private String canOverwrite;

    @Column(name = "device_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "关联设备")
    private Long deviceId;

    @Column(name = "task_remark")
    @ApiModelProperty(value = "备注")
    private String taskRemark;

    @Column(name = "task_status")
    @ApiModelProperty(value = "任务状态")
    private String taskStatus;

    @Transient
    private String startNow;

    public void copy(NtsMongoToMysqlTask source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}