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
package me.zhengjie.ntsprepdata.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-02-27
**/
@Data
public class NtsPrepFlowDataDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 流量产生时间 */
    private Timestamp fdDateTime;

    /** 流量值 */
    private BigDecimal fdValue;

    /** 标准化流量值 */
    private BigDecimal normFdValue;

    /** 协议 */
    private String fdProtocol;

    /** 流量单位 */
    private String fdWeight;

    /** 关联设备主键 */
    private Long deviceId;

    /** 关联同步任务主键 */
    private Long taskId;

    /** 数据入库时间 */
    private Timestamp createTime;

}