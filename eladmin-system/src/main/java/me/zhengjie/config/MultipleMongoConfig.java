package me.zhengjie.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import me.zhengjie.config.props.MultipleMongoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MultipleMongoConfig {

	@Autowired
    private MultipleMongoProperties mongoProperties;

	@Primary
	@Bean(name = "dboriMongoTemplate")
	public MongoTemplate dboriMongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory(this.mongoProperties.getDbori()));
	}

	@Bean
	@Qualifier("dbprepMongoTemplate")
	public MongoTemplate dbprepMongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(this.mongoProperties.getDbprep()));
	}

	public MongoDbFactory mongoDbFactory(MongoProperties mongo) throws Exception {
		MongoClient client = new MongoClient(mongo.getHost(), mongo.getPort());
		return new SimpleMongoDbFactory(client, mongo.getDatabase());
	}
}