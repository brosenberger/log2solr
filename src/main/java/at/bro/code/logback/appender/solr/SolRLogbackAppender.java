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

    public SolRLogbackAppender() {
        System.out.println("initialized appender");
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