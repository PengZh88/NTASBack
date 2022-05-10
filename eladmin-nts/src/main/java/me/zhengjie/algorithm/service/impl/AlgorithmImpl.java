package me.zhengjie.algorithm.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.algorithm.service.NtsAlgorithmImplService;
import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionParameters;
import net.seninp.jmotif.sax.discord.DiscordRecords;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AlgorithmImpl implements NtsAlgorithmImplService {
    protected final String PAA_SIZE = "PAA_SIZE";
    protected final String ALPHABET_SIZE = "ALPHABET_SIZE";
    protected final String DISCORDS_TO_TEST = "DISCORDS_TO_TEST";
    protected final String NORM_THRESHOLD = "NORM_THRESHOLD";

    private Map<String, String> list2Map(List<NtsAnomalyDetectionParameters> params) {
        Map<String, String> map = new HashMap<String, String>();
        if (params != null && !params.isEmpty()) {
            for (NtsAnomalyDetectionParameters dto : params) {
                map.put(dto.getPmLabel(), dto.getPmValue());
            }
        }
        return map;
    }

    public DiscordRecords series2AnomaliesRecords(double[] series, int windowSize, Map<String, String> params) {
        return null;
    }

    @Override
    public DiscordRecords series2AnomaliesRecords(double[] series, int windowSize, List<NtsAnomalyDetectionParameters> params) {
        return series2AnomaliesRecords(series, windowSize, list2Map(params));
    }
}
