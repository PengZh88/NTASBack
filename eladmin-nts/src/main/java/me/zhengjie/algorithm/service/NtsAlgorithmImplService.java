package me.zhengjie.algorithm.service;

import me.zhengjie.anomalydetection.domain.NtsAnomalyDetectionParameters;
import net.seninp.jmotif.sax.discord.DiscordRecords;

import java.util.List;

/**
 * 异常检测算法实现类接口
 */
public interface NtsAlgorithmImplService {
    DiscordRecords series2AnomaliesRecords(double[] series, int windowSize, List<NtsAnomalyDetectionParameters> params);
}
