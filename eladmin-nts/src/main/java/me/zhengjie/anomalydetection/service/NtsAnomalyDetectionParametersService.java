package me.zhengjie.anomalydetection.service;

import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersDto;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionParameters;

import java.util.List;

public interface NtsAnomalyDetectionParametersService {
    void createObjs(Long taskId, List<NtsAlgorithmParametersDto> dtos);
    void deleteByTaskId(Long taskId);
    List<NtsAnomalyDetectionParameters> queryByTaskId(Long taskId);
}
