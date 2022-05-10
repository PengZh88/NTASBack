package me.zhengjie.anomalydetection.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersDto;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdStrategyDto implements Serializable {
    /** 检测方法名 */
    private String methodName;

    /** 参数 */
    private List<NtsAlgorithmParametersDto> paras;
}
