package com.spaco_apoio.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "SUPERVISOR")
@Getter
@Setter
@NoArgsConstructor
public class Supervisor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USE_ID")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "USE_ID")
    private Users user;

    @Column(name = "SUP_OBSERVATION")
    private String observation;


}
