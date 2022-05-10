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
package me.zhengjie.device.service.dto;

import lombok.Data;
import me.zhengjie.carbinet.service.dto.NtsCarbinetDto;
import me.zhengjie.carbinet.service.dto.NtsSmallCarbinetDto;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-12-19
**/
@Data
public class NtsDeviceDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 设备名称 */
    private String devName;

    /** 设备编号 */
    private String devNum;

    /** 设备负责人 */
    private String devChargePerson;

    /** 设备联系人 */
    private String devContactPerson;

    /** IP地址 */
    private String devIpAddr;

    /** MAC地址 */
    private String devMacAddr;

    /** 设备品牌 */
    private String devBrand;

    /** 设备型号 */
    private String devModel;

    /** 所在机柜 */
    private NtsSmallCarbinetDto carbinet;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updatedBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}