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
package me.zhengjie.carbinet.service.dto;

import lombok.Data;
import me.zhengjie.machineroom.service.dto.NtsSmallMachineroomDto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-08-18
**/
@Data
public class NtsCarbinetDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 机柜编号 */
    private String jgNum;

    /** 机柜名称 */
    private String jgName;

    /** 备注 */
    private String jgRemark;

    /** 机房主键 */
    private NtsSmallMachineroomDto machineroom;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updatedBy;

    /** 创建日期 */
    private Timestamp createTime;

    /** 更新日期 */
    private Timestamp updateTime;

    public String getVoName() {
        return machineroom.getRoomName() + "-" + jgName;
    }
}