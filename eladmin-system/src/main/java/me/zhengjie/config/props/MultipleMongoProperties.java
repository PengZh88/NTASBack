package me.zhengjie.config.props;

import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="mongodb")
public class MultipleMongoProperties {
    private MongoProperties dbori =new MongoProperties();
    private MongoProperties dbprep =new MongoProperties();

    public MongoProperties getDbori() {
        return dbori;
    }

    public void setDbori(MongoProperties dbori) {
        this.dbori = dbori;
    }

    public MongoProperties getDbprep() {
        return dbprep;
    }

    public void setDbprep(MongoProperties dbprep) {
        this.dbprep = dbprep;
    }
}
