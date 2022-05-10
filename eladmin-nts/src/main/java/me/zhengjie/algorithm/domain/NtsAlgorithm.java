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
@Table(name="nts_algorithm")
public class NtsAlgorithm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "alg_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "算法名称")
    private String algName;

    @Column(name = "alg_desc",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "算法简要描述")
    private String algDesc;

    @Column(name = "paper_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "相关论文")
    private String paperName;

    @Column(name = "paper_year",nullable = false)
    @NotNull
    @ApiModelProperty(value = "论文发表年")
    private Integer paperYear;

    @Column(name = "paper_jcname",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "期刊（会议）名称")
    private String paperJcname;

    @Column(name = "paper_type",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "论文类型")
    private String paperType;

    @Column(name = "paper_include",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "收录情况")
    private String paperInclude;

    @Column(name = "paper_authors",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "论文作者")
    private String paperAuthors;

    public void copy(NtsAlgorithm source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}