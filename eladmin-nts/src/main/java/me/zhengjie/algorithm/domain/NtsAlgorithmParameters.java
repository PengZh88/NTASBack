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
package me.zhengjie.algorithm.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author Peng Zhan
* @date 2020-12-26
**/
@Entity
@Data
@Table(name="nts_algorithm_parameters")
public class NtsAlgorithmParameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "par_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "参数名称")
    private String parName;

    @Column(name = "par_value",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "参数值")
    private String parValue;

    @Column(name = "par_remark")
    @ApiModelProperty(value = "备注")
    private String parRemark;

    @Column(name = "alg_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "关联算法")
    private Long algId;

    public void copy(NtsAlgorithmParameters source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}