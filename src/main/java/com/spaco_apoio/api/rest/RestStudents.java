package com.spaco_apoio.api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spaco_apoio.api.mapper.StudentsMapper;
import com.spaco_apoio.api.model.Students;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class RestStudents implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "user")
    private RestUsers user;

    @JsonProperty(value = "comments")
    private String comments;

    @JsonProperty(value = "birthDate")
    private LocalDate birthDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RestUsers getUser() {
        return user;
    }

    public void setUser(RestUsers user) {
        this.user = user;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Students restToModel(){
        return StudentsMapper.INSTANCE.convertToModel(this);
    }
}
