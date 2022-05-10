package me.zhengjie.anomalydetection.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Table(name="nts_anomaly_detection_parameters")
public class NtsAnomalyDetectionParameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "pm_label",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "参数名称")
    private String pmLabel;

    @Column(name = "pm_value",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "参数值")
    private String pmValue;

    @Column(name = "task_id",nullable = false)
    @NotNull
    @ApiModelProperty(value = "关联异常检测任务")
    private Long taskId;

    public void copy(NtsAnomalyDetectionParameters source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
