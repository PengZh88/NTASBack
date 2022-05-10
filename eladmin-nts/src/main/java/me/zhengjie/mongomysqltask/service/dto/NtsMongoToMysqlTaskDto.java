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
package me.zhengjie.mongomysqltask.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2021-02-20
**/
@Data
public class NtsMongoToMysqlTaskDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 任务名称 */
    private String taskName;

    /** MongoDBCollection */
    private String collectionName;

    /** 网络协议 */
    private String fdProtocol;

    /** 开始时间 */
    private Timestamp fdStartDate;

    /** 结束时间 */
    private Timestamp fdEndDate;

    /** 是否覆盖 */
    private String canOverwrite;

    /** 关联设备 */
    private Long deviceId;

    /** 备注 */
    private String taskRemark;

    /** 任务状态 */
    private String taskStatus;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updatedBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;

    /** 立即启动 */
    private String startNow;
}