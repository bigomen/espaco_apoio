package com.spaco_apoio.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "mail")
@Component
public class EmailConfig {

    private String email;
    private String subjectCreate;
    private String subjectReset;
    private String contentCreate;
    private String contentReset;
    private String key;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubjectCreate() {
        return subjectCreate;
    }

    public void setSubjectCreate(String subjectCreate) {
        this.subjectCreate = subjectCreate;
    }

    public String getSubjectReset() {
        return subjectReset;
    }

    public void setSubjectReset(String subjectReset) {
        this.subjectReset = subjectReset;
    }

    public String getContentCreate() {
        return contentCreate;
    }

    public void setContentCreate(String contentCreate) {
        this.contentCreate = contentCreate;
    }

    public String getContentReset() {
        return contentReset;
    }

    public void setContentReset(String contentReset) {
        this.contentReset = contentReset;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
