package com.spaco_apoio.api.model;

import com.spaco_apoio.api.constants.Constants;
import com.spaco_apoio.api.mapper.StudentsMapper;
import com.spaco_apoio.api.rest.RestStudents;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.utility.RandomString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "STUDENTS")
@Getter
@Setter
@NoArgsConstructor
public class Students implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USE_ID")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "USE_ID")
    private Users user;

    @Column(name = "STU_COMMENTS")
    private String comments;

    @Column(name = "STU_BIRTH_DATE")
    private LocalDate birthDate;

    public Students(Long id, String name, LocalDate startDate, LocalDate endDate, String statusDescription){
        Users users = new Users();
        users.setName(name);
        users.setStartDate(startDate);
        users.setEndDate(endDate);
        UsersStatus status = new UsersStatus();
        status.setDescription(statusDescription);
        users.setStatus(status);
        this.id = id;
        this.user = users;
    }

    public Students(Long id, String comments, LocalDate birthDate, String name, String email, String cpf,
                    LocalDate startDate, LocalDate endDate, Long statusId){
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setCpf(cpf);
        user.setStartDate(startDate);
        user.setEndDate(endDate);
        user.setStatusId(statusId);
        this.id = id;
        this.comments = comments;
        this.birthDate = birthDate;
        this.user = user;
    }

    @PrePersist
    public void prePersist(){
        this.user.setProfileId(Constants.USER_PROFILE_STUDENT);
        this.user.setStatusId(Constants.USER_STATUS_INACTIVE);
        this.user.setStartDate(LocalDate.now());
        this.user.setResetToken(RandomString.make(50));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
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

    public RestStudents modelToRest(){
        return StudentsMapper.INSTANCE.convertToRest(this);
    }
}
