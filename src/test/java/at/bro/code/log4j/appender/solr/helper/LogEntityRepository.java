package at.bro.code.log4j.appender.solr.helper;

import org.springframework.data.solr.repository.SolrCrudRepository;

public interface LogEntityRepository extends SolrCrudRepository<LogEntity, String> {

}
