package me.zhengjie.mongoapp.service.impl;

import cn.hutool.core.date.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import me.zhengjie.base.ListOptionInfo;
import me.zhengjie.mongoapp.repository.dbori.NtsMdbOriRepository;
import me.zhengjie.mongoapp.service.NtsDboriService;
import me.zhengjie.mongoapp.task.NtsMongoTaskUtils;
import me.zhengjie.utils.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NtsDboriServiceImpl implements NtsDboriService {
    private final NtsMdbOriRepository ntsMdbOriRepository;

    @Override
    public Object getMDbSize() {
        return ntsMdbOriRepository.getDbSize();
    }

    @Override
    public List<ListOptionInfo> getDbInfo() {
        return ntsMdbOriRepository.getDbInfo();
    }

    @Override
    public void collectionStatisticsTask() {
        MongoDatabase db = ntsMdbOriRepository.getDb();
        // Step 1: Drop the former collection.
        MongoCollection stc = db.getCollection(NtsMongoTaskUtils.DBORISTATISTICS);
        stc.drop();

        // Step 2: Create new collection.
        db.createCollection(NtsMongoTaskUtils.DBORISTATISTICS);

        // Step 3: Insert statistics data into the new collection.
        MongoCursor mc = db.listCollections().iterator();
        while(mc.hasNext()) {
            Document doc = (Document) mc.next();
            String collectionName = (String) doc.get("name");
            if (!collectionName.startsWith("ori_ipv6"))
                continue;

            BasicDBObject fields = new BasicDBObject();
            fields.put("collStats", collectionName);
            fields.put("scale", 1);
            Document document = db.runCommand(fields);

            int countD = (int) document.get("count");
            String storageSize = StringUtils.storageSize((Double) document.get("storageSize"));
            double ssd = (Double) document.get("storageSize") / 1024 /1024/1024; // GB
            String weight = "GB";

            Document insertD = new Document();
            insertD.append("cname", collectionName);
            insertD.append("countd", countD);
            insertD.append("storages", storageSize);
            insertD.append("ssd", ssd);
            insertD.append("weight", weight);
            insertD.append("datet", DateUtil.now());
            ntsMdbOriRepository.insertOne(NtsMongoTaskUtils.DBORISTATISTICS, insertD);
        }
    }

    @Override
    public Map<String, Object> barOriStaData() {
        return ntsMdbOriRepository.barOriStaData();
    }
}
