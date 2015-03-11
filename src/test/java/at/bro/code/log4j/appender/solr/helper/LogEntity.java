package at.bro.code.log4j.appender.solr.helper;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

public class LogEntity {

    @Id
    @Field("uid")
    private String uid;

    @Field("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
