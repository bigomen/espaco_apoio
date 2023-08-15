package com.spaco_apoio.api.model;

import com.spaco_apoio.api.constants.Constants;
import com.spaco_apoio.api.mapper.UsersMapper;
import com.spaco_apoio.api.rest.RestUsers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
public class Users implements Serializable, UserDetails {

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

    public Users(String email, LocalDate endDate, Long statusId, String profileDescription, String password) {
        this.email = email;
        this.endDate = endDate;
        this.statusId = statusId;
        UsersProfile profile = new UsersProfile();
        profile.setDescription(profileDescription);
        this.profile = profile;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UsersProfile> profileList = new ArrayList<>();
        profileList.add(this.profile);
        return profileList;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Objects.isNull(this.endDate) || LocalDate.now().isBefore(this.endDate);
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Objects.equals(this.statusId, Constants.USER_STATUS_ACTIVE);
    }

    public RestUsers modelToRest(){
        return UsersMapper.INSTANCE.convertToRest(this);
    }

}
