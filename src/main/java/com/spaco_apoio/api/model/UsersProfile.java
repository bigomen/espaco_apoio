package com.spaco_apoio.api.model;

import com.spaco_apoio.api.mapper.UsersProfileMapper;
import com.spaco_apoio.api.rest.RestUsersProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "USERS_PROFILE")
@Getter
@Setter
@Immutable
public class UsersProfile extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "UPR_ID")
    private Long id;

    @Column(name = "UPR_DESCRIPTION")
    private String description;

    public RestUsersProfile modelToRest(){
        return UsersProfileMapper.INSTANCE.convertToRest(this);
    }
}
