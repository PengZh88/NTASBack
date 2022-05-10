package me.zhengjie.mongoapp.service.impl;

import cn.hutool.core.date.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import me.zhengjie.base.ListOptionInfo;
import me.zhengjie.mongoapp.repository.dbprep.NtsMdbPrepRepository;
import me.zhengjie.mongoapp.service.NtsDbprepService;
import me.zhengjie.mongoapp.task.NtsMongoTaskUtils;
import me.zhengjie.utils.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NtsDbprepServiceImpl implements NtsDbprepService {
    private final NtsMdbPrepRepository ntsMdbPrepRepository;

    @Override
    public Object getMDbSize() {
        return ntsMdbPrepRepository.getDbSize();
    }

    @Override
    public List<ListOptionInfo> getDbInfo() {
        return ntsMdbPrepRepository.getDbInfo();
    }

    @Override
    public void collectionStatisticsTask() {
        MongoDatabase db = ntsMdbPrepRepository.getDb();
        // Step 1: Drop the former collection.
        MongoCollection stc = db.getCollection(NtsMongoTaskUtils.DBPREPSTATISTICS);
        stc.drop();

        // Step 2: Create new collection.
        db.createCollection(NtsMongoTaskUtils.DBPREPSTATISTICS);

        // Step 3: Insert statistics data into the new collection.
        MongoCursor mc = db.listCollections().iterator();
        while(mc.hasNext()) {
            Document doc = (Document) mc.next();
            String collectionName = (String) doc.get("name");
            if (!collectionName.startsWith("prep_ipv6"))
                continue;



            BasicDBObject fields = new BasicDBObject();
            fields.put("collStats", collectionName);
            fields.put("scale", 1);
            Document document = db.runCommand(fields);

            int countD = (int) document.get("count");
            String storageSize = StringUtils.storageSize((int) document.get("storageSize"));
            double ssd = (int) document.get("storageSize") / 1024.0; // KB
            String weight = "KB";

            Document insertD = new Document();
            insertD.append("cname", collectionName);
            insertD.append("countd", countD);
            insertD.append("storages", storageSize);
            insertD.append("ssd", ssd);
            insertD.append("weight", weight);
            insertD.append("datet", DateUtil.now());
            ntsMdbPrepRepository.insertOne(NtsMongoTaskUtils.DBPREPSTATISTICS, insertD);
        }
    }

    @Override
    public Map<String, Object> barPrepStaData() {
        return ntsMdbPrepRepository.barPrepStaData();
    }

    @Override
    public List<ListOptionInfo> getCollections() {
        return ntsMdbPrepRepository.getCollections();
    }
}
