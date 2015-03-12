package at.bro.code.logback.appender.solr;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.apache.solr.common.SolrInputDocument;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

public class SolRLogbackAppender<E> extends UnsynchronizedAppenderBase<E> {

    private String solrServer;
    private int queueSize = 10;
    private int threadCount = 10;
    private int connectionTimeOut = 1000;
    private ConcurrentUpdateSolrServer solrClient;
    private String informationEnricherClass;
    private SolrDocumentEnrichter enricher;

    public SolRLogbackAppender() {
        System.out.println("initialized appender");
    }

    public String getInformationEnricherClass() {
        return informationEnricherClass;
    }

    public void setInformationEnricherClass(String informationEnricherClass) {
        this.informationEnricherClass = informationEnricherClass;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(int connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public String getSolrServer() {
        return solrServer;
    }

    public void setSolrServer(String solrServer) {
        this.solrServer = solrServer;
    }

    @Override
    public void start() {
        solrClient = new ConcurrentUpdateSolrServer(solrServer, queueSize, threadCount);
        solrClient.setConnectionTimeout(connectionTimeOut);

        if (informationEnricherClass != null && informationEnricherClass.length() > 0) {
            try {
                final Object enricher = Class.forName(informationEnricherClass).newInstance();
                if (enricher instanceof SolrDocumentEnrichter) {
                    this.enricher = (SolrDocumentEnrichter) enricher;
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.start();
    }

    @Override
    public void stop() {
        solrClient.shutdown();
        super.stop();
    }

    @Override
    protected void append(E eventObject) {
        if (solrClient != null) {

            final SolrInputDocument doc = new SolrInputDocument();

            doc.addField("id", UUID.randomUUID().toString());
            if (eventObject instanceof LoggingEvent) {
                final LoggingEvent event = (LoggingEvent) eventObject;
                doc.addField("time_dt", new Date(event.getTimeStamp()));
                doc.addField("message_s", event.getMessage());
                doc.addField("formatted_message_s", event.getFormattedMessage());
                doc.addField("level_s", event.getLevel());
                if (enricher != null) {
                    enricher.enrichDocument(event, doc);
                }
            }

            try {
                if (solrClient.ping() != null) {
                    solrClient.add(doc);
                    solrClient.commit();
                } else {
                    System.out.println(doc.toString());
                }
            } catch (SolrServerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}