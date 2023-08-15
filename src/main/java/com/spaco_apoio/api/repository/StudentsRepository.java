package com.spaco_apoio.api.repository;

import com.spaco_apoio.api.model.Students;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsRepository extends CrudRepository<Students, Long>, StudentsRepositoryCustom{
}
