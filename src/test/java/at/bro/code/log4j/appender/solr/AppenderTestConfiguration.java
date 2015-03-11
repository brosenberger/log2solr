package at.bro.code.log4j.appender.solr;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@ComponentScan
@EnableSolrRepositories("at.bro.code.log4j.appender.solr")
@Configuration
public class AppenderTestConfiguration {

}
