package com.spaco_apoio.api.repository;

import com.spaco_apoio.api.model.Students;
import com.spaco_apoio.api.rest.params.StudentsParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentsRepositoryCustom {

    Page<Students> list(StudentsParams params, Pageable pageable);

    Students details(Long id);
}
