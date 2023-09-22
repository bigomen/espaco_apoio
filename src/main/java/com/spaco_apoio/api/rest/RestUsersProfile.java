package com.spaco_apoio.api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spaco_apoio.api.mapper.UsersProfileMapper;
import com.spaco_apoio.api.model.UsersProfile;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class RestUsersProfile extends BaseRestModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "description")
    private String description;

    public UsersProfile restToModel(){
        return UsersProfileMapper.INSTANCE.convertToModel(this);
    }
}
