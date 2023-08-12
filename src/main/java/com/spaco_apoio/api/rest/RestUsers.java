package com.spaco_apoio.api.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spaco_apoio.api.mapper.UsersMapper;
import com.spaco_apoio.api.model.Users;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class RestUsers extends BaseRestModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "cpf")
    private String cpf;

    @JsonProperty(value = "startDate")
    private LocalDate startDate;

    @JsonProperty(value = "endDate")
    private LocalDate endDate;

    @JsonProperty(value = "statusId")
    private String statusId;

    @JsonProperty(value = "status")
    private RestUsersStatus status;

    @JsonProperty(value = "profileId")
    private String profileId;

    @JsonProperty(value = "profile")
    private RestUsersProfile profile;

    public Users restToModel(){
        return UsersMapper.INSTANCE.convertToModel(this);
    }
}
