package me.zhengjie.mongoapp.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.mongoapp.service.NtsDboriService;
import me.zhengjie.mongoapp.service.NtsDbprepService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NtsMongoTask {
    private final NtsDboriService ntsDboriService;
    private final NtsDbprepService ntsDbprepService;

    public void dboriCollectionStatistics() {
        log.info("mongoCollectionStatistics 开始--统计dbori中Collection数据量");
        ntsDboriService.collectionStatisticsTask();
        log.info("mongoCollectionStatistics 结束--统计dbori中Collection数据量");
    }

    public void dbprepCollectionStatistics() {
        log.info("mongoCollectionStatistics 开始--统计dbprep中Collection数据量");
        ntsDbprepService.collectionStatisticsTask();
        log.info("mongoCollectionStatistics 结束--统计dbprep中Collection数据量");
    }
}
