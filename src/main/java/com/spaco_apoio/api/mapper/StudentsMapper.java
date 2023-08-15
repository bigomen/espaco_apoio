package com.spaco_apoio.api.mapper;

import com.spaco_apoio.api.model.Students;
import com.spaco_apoio.api.rest.RestStudents;
import com.spaco_apoio.api.utility.UtilSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class, UsersMapper.class})
public interface StudentsMapper {

    StudentsMapper INSTANCE = Mappers.getMapper(StudentsMapper.class);

    RestStudents convertToRest(Students students);

    Students convertToModel(RestStudents restStudents);
}
