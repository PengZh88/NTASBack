package me.zhengjie.anomalydetection.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.algorithm.service.dto.NtsAlgorithmParametersDto;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionParameters;
import me.zhengjie.anomalydetection.repository.NtsAnomalyDetectionParametersRepository;
import me.zhengjie.anomalydetection.service.NtsAnomalyDetectionParametersService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NtsAnomalyDetectionParametersServiceImpl implements NtsAnomalyDetectionParametersService {

    private final NtsAnomalyDetectionParametersRepository ntsAnomalyDetectionParametersRepository;

    @Override
    public void createObjs(Long taskId, List<NtsAlgorithmParametersDto> dtos) {
        if(dtos != null && !dtos.isEmpty()) {
            List<NtsAnomalyDetectionParameters> parameters = new ArrayList<NtsAnomalyDetectionParameters>();

            for(NtsAlgorithmParametersDto dto : dtos) {
                NtsAnomalyDetectionParameters pp = new NtsAnomalyDetectionParameters();
                pp.setTaskId(taskId);
                pp.setPmLabel(dto.getParName());
                pp.setPmValue(dto.getParValue());
                parameters.add(pp);
            }

            ntsAnomalyDetectionParametersRepository.saveAll(parameters);

        }
    }

    @Override
    @Transactional
    public void deleteByTaskId(Long taskId) {
        ntsAnomalyDetectionParametersRepository.deleteByTaskId(taskId);
    }

    @Override
    public List<NtsAnomalyDetectionParameters> queryByTaskId(Long taskId) {
        return ntsAnomalyDetectionParametersRepository.queryByTaskId(taskId);
    }
}
