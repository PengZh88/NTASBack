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
package me.zhengjie.building.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;

import me.zhengjie.base.BaseEntity;

import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-08-05
**/
@Entity
@Data
@Table(name="nts_building")
public class NtsBuilding extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "building_name",unique = true,nullable = false)
    @NotBlank
    @ApiModelProperty(value = "楼宇名称")
    private String buildingName;

    @Column(name = "building_num",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "楼宇编号")
    private String buildingNum;

    @Column(name = "building_address",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "地址")
    private String buildingAddress;

    @Column(name = "building_postal",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "邮编")
    private String buildingPostal;

    @Column(name = "building_contact",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "联系电话")
    private String buildingContact;


    public void copy(NtsBuilding source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}