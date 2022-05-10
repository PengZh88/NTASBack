package me.zhengjie.config;

import me.zhengjie.config.props.MultipleMongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EnableConfigurationProperties(MultipleMongoProperties.class)
@EnableMongoRepositories(basePackages = "me.zhengjie.mongoapp",
		mongoTemplateRef = "dbprepMongoTemplate")
public class DbprepMongoConfig {

}
