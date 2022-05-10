package me.zhengjie.mongoapp.repository.dbprep;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.zhengjie.base.ListOptionInfo;
import me.zhengjie.mongoapp.domain.DbprepStatustics;
import me.zhengjie.utils.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;

@Repository
public class NtsMdbPrepRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public NtsMdbPrepRepository(@Qualifier("dbprepMongoTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public MongoDatabase getDb() {
        return mongoTemplate.getDb();
    }

    public void insertOne(String collectionName, Document document) {
        getDb().getCollection(collectionName).insertOne(document);
    }

    public Object getDbSize() {
        BasicDBObject fields = new BasicDBObject();
        fields.put("dbStats", 1);
        Document document = getDb().runCommand(fields);
        Double ss = (Double) document.get("storageSize");
        return StringUtils.storageSize(ss);
    }

    public List<ListOptionInfo> getDbInfo() {
        DecimalFormat df = new DecimalFormat("#,###");
        List<ListOptionInfo> opts = new ArrayList<ListOptionInfo>();
        BasicDBObject fields = new BasicDBObject();
        fields.put("dbStats", 1);
        Document document = getDb().runCommand(fields);
        Iterator<String> it = document.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = "";
            if (key.equals("storageSize") || key.equals("fsUsedSize") || key.equals("fsTotalSize")) {
                value = StringUtils.storageSize((Double) document.get(key));
            } else if (document.get(key) instanceof Integer || document.get(key) instanceof Double) {
                value = df.format(document.get(key));
            } else {
                value = document.get(key).toString();
            }
            ListOptionInfo info = new ListOptionInfo(key, key, value);
            opts.add(info);
        }
        return opts;
    }

    public Map<String, Object> barPrepStaData() {
        Sort sort = new Sort(Sort.Direction.ASC, "cname");
        Criteria criteria = Criteria.where("countd").gt(0);
        Query query = new Query(criteria);
        List<DbprepStatustics> list = mongoTemplate.find(query.with(sort), DbprepStatustics.class);
        Map<String, Object> map = new HashMap<String, Object>();
        if (list != null && !list.isEmpty()) {
            List<String> xaxis = new ArrayList<String>();
            List<Integer> countds = new ArrayList<Integer>();
            List<Double> ssds = new ArrayList<Double>();
            for (DbprepStatustics ds : list) {
                xaxis.add(ds.getCname());
                countds.add(ds.getCountd());
                ssds.add(((new BigDecimal(ds.getSsd())).setScale(2, BigDecimal.ROUND_HALF_UP)).doubleValue());
            }
            map.put("prepxaxis", xaxis);
            map.put("prepcountds", countds);
            map.put("prepssds", ssds);
        }
        return map;
    }

    public List<ListOptionInfo> getCollections() {
        List<ListOptionInfo> opts = new ArrayList<ListOptionInfo>();
        Iterator it = this.getDb().listCollectionNames().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            if (name.startsWith("prep_ipv6")) {
                opts.add(new ListOptionInfo(name, name, name));
            }
        }
        return opts;
    }

    public Iterator<Document> findCollectionQueryData(String collectionName, Timestamp startTime, Timestamp endTime, String protocol) {
        MongoCollection collection = mongoTemplate.getCollection(collectionName);
        Document filter = new Document();
        filter.append("protocol", protocol);
        filter.append("fdatetime", new Document("$gte", DateUtil.formatLocalDateTime(startTime.toLocalDateTime())).append("$lte", DateUtil.formatLocalDateTime(endTime.toLocalDateTime())));
        FindIterable<Document> iterable = collection.find(filter).sort(new Document("fdatetime", 1)); // 1 ASC  -1 DESC
        Iterator<Document> it = iterable.iterator();
        return it;
    }
}
