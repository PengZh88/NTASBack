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
package me.zhengjie.carbinet.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.zhengjie.base.BaseEntity;
import me.zhengjie.machineroom.domain.NtsMachineroom;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-08-18
**/
@Entity
@Data
@Table(name="nts_carbinet")
public class NtsCarbinet extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "jg_num",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "机柜编号")
    private String jgNum;

    @Column(name = "jg_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "机柜名称")
    private String jgName;

    @Column(name = "jg_remark")
    @ApiModelProperty(value = "备注")
    private String jgRemark;

    @JoinColumn(name = "machineroom_id", nullable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    @ApiModelProperty(value = "机房", hidden = true)
    private NtsMachineroom machineroom;

    public void copy(NtsCarbinet source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}