package com.spaco_apoio.api.model;

import com.spaco_apoio.api.mapper.StudentsMapper;
import com.spaco_apoio.api.rest.RestStudents;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
