package com.spaco_apoio.api.model;

import com.spaco_apoio.api.mapper.UsersStatusMapper;
import com.spaco_apoio.api.rest.RestUsersStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "USERS_STATUS")
@Getter
@Setter
@Immutable
@NoArgsConstructor
public class UsersStatus extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "UST_ID")
    private Long id;

    @Column(name = "UST_DESCRIPTION")
    private String description;

    public UsersStatus(Long id) {
        this.id = id;
    }

    public RestUsersStatus modelToRest(){
        return UsersStatusMapper.INSTANCE.convertToRest(this);
    }
}
