package at.bro.code.log4j.appender.solr.helper;

import org.apache.solr.client.solrj.beans.Field;

public class LogEntity {

    @Field("id")
    private String id;

    @Field("message_s")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
