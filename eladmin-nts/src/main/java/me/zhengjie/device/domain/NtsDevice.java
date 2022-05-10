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
package me.zhengjie.device.domain;

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
import me.zhengjie.carbinet.domain.NtsCarbinet;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-12-19
**/
@Entity
@Data
@Table(name="nts_device")
public class NtsDevice extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "dev_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设备名称")
    private String devName;

    @Column(name = "dev_num",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设备编号")
    private String devNum;

    @Column(name = "dev_charge_person",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设备负责人")
    private String devChargePerson;

    @Column(name = "dev_contact_person",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设备联系人")
    private String devContactPerson;

    @Column(name = "dev_ip_addr",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "IP地址")
    private String devIpAddr;

    @Column(name = "dev_mac_addr",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "MAC地址")
    private String devMacAddr;

    @Column(name = "dev_brand",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设备品牌")
    private String devBrand;

    @Column(name = "dev_model",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "设备型号")
    private String devModel;

    @JoinColumn(name = "carbinet_id")
    @ManyToOne(fetch=FetchType.LAZY)
    @ApiModelProperty(value = "所在机柜", hidden = true)
    private NtsCarbinet carbinet;

    public void copy(NtsDevice source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}