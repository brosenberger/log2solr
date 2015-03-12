package at.bro.code.logback.appender.solr;

import org.apache.solr.common.SolrInputDocument;

import ch.qos.logback.classic.spi.LoggingEvent;

public interface SolrDocumentEnrichter {
    void enrichDocument(LoggingEvent event, SolrInputDocument doc);
}
