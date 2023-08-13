package com.spaco_apoio.api.model;

import com.spaco_apoio.api.mapper.UsersMapper;
import com.spaco_apoio.api.rest.RestUsers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class Users implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USE_ID")
    private Long id;

    @Column(name = "USE_NAME")
    private String name;

    @Column(name = "USE_EMAIL")
    private String email;

    @Column(name = "USE_CPF")
    private String cpf;

    @Column(name = "USE_START_DATE")
    private LocalDate startDate;

    @Column(name = "USE_END_DATE")
    private LocalDate endDate;

    @Column(name = "UST_ID")
    private Long statusId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UST_ID", updatable = false, insertable = false)
    private UsersStatus status;

    @Column(name = "UPR_ID")
    private Long profileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPR_ID", updatable = false, insertable = false)
    private UsersProfile profile;

    @Column(name = "USE_PASSWORD")
    private String password;

    @Column(name = "USE_RESET_TOKEN")
    private String resetToken;

    public Users(Long id, String resetToken) {
        this.id = id;
        this.resetToken = resetToken;
    }

    public Users(LocalDate startDate, LocalDate endDate, String password) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.password = password;
    }

    public RestUsers modelToRest(){
        return UsersMapper.INSTANCE.convertToRest(this);
    }

}
