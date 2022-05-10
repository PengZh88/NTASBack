package me.zhengjie.algorithm.factory;

import me.zhengjie.algorithm.service.NtsAlgorithmImplService;
import me.zhengjie.utils.SpringContextHolder;

public class AlgorithmCreateFactory {
    public static NtsAlgorithmImplService createAlgorithm(String method) {
        NtsAlgorithmImplService service = null;
        try {
            service = SpringContextHolder.getBean(method + "AlgorithmImpl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }
}
