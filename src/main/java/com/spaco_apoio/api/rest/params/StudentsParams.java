package com.spaco_apoio.api.rest.params;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentsParams {

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
