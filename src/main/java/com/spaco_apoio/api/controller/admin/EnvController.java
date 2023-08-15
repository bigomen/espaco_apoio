package com.spaco_apoio.api.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;

public class EnvController{

    @Autowired
    private Environment env;

    public HashMap<String, String> getEmailPropertiesCreate(){
        HashMap<String, String> props = new HashMap<>();
        props.put("senderMail", env.getProperty("sendgrid.email"));
        props.put("subject", env.getProperty("sendgrid.subject.create"));
        props.put("content", env.getProperty("sendgrid.content.create"));
        props.put("key", env.getProperty("sendgrid.key"));
        return props;
    }


}
