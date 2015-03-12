package at.bro.code.log4j.appender.solr.enricher;

import org.apache.solr.common.SolrInputDocument;

import at.bro.code.logback.appender.solr.SolrDocumentEnrichter;
import ch.qos.logback.classic.spi.LoggingEvent;

public class TestEnricher implements SolrDocumentEnrichter {

    @Override
    public void enrichDocument(LoggingEvent event, SolrInputDocument doc) {
        doc.addField("enriched_s", "some fancy value");
    }

}
