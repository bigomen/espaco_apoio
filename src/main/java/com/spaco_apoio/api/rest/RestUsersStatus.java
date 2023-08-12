package com.spaco_apoio.api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spaco_apoio.api.mapper.UsersStatusMapper;
import com.spaco_apoio.api.model.UsersStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class RestUsersStatus extends BaseRestModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "description")
    private String description;

    public UsersStatus restToModel(){
        return UsersStatusMapper.INSTANCE.convertToModel(this);
    }
}
