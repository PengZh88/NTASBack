package me.zhengjie.mongoapp.service;

import me.zhengjie.base.ListOptionInfo;

import java.util.List;
import java.util.Map;

public interface NtsDbprepService {
    Object getMDbSize();
    List<ListOptionInfo> getDbInfo();
    void collectionStatisticsTask();
    Map<String, Object> barPrepStaData();
    List<ListOptionInfo> getCollections();
}
