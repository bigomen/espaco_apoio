package com.spaco_apoio.api.rest;

public abstract class BaseRestModel {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
}
