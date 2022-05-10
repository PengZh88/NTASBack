package me.zhengjie.anomalydetection.context;

import me.zhengjie.anomalydetection.strategy.AnomalyDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 策略模式上下文Context
 */
@Component
public class AnomalyDetectionStrategyContext {

    @Autowired
    private Map<String, AnomalyDetectionStrategy> strategyMap;


}
