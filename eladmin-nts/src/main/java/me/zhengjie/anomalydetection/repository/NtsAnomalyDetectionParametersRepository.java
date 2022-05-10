package me.zhengjie.anomalydetection.repository;

import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NtsAnomalyDetectionParametersRepository extends JpaRepository<NtsAnomalyDetectionParameters, Long>, JpaSpecificationExecutor<NtsAnomalyDetectionParameters> {
    void deleteByTaskId(Long taskId);
    List<NtsAnomalyDetectionParameters> queryByTaskId(Long taskId);
}
