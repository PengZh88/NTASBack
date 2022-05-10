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
package me.zhengjie.algorithm.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-12-26
**/
@Data
public class NtsAlgorithmDto implements Serializable {

    /** 主键 */
    private Long id;

    /** 算法名称 */
    private String algName;

    /** 算法简要描述 */
    private String algDesc;

    /** 相关论文 */
    private String paperName;

    /** 论文发表年 */
    private Integer paperYear;

    /** 期刊（会议）名称 */
    private String paperJcname;

    /** 论文类型 */
    private String paperType;

    /** 收录情况 */
    private String paperInclude;

    /** 论文作者 */
    private String paperAuthors;
}