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
package me.zhengjie.machineroom.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.zhengjie.base.BaseEntity;
import me.zhengjie.building.domain.NtsBuilding;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-08-06
**/
@Entity
@Data
@Table(name="nts_machineroom")
public class NtsMachineroom extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "room_name",unique = true,nullable = false)
    @NotBlank
    @ApiModelProperty(value = "机房名称")
    private String roomName;

    @JoinColumn(name = "building_id")
    @ManyToOne(fetch=FetchType.LAZY)
    @ApiModelProperty(value = "楼宇", hidden = true)
    private NtsBuilding building;

    @Column(name = "floor_number",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "楼层")
    private String floorNumber;

    @Column(name = "cabinet_number",nullable = false)
    @NotNull
    @ApiModelProperty(value = "机柜数")
    private Integer cabinetNumber;

    @Column(name = "charge_person",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "管理员")
    private String chargePerson;

    @Column(name = "telephone",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "联系电话")
    private String telephone;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    public void copy(NtsMachineroom source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}