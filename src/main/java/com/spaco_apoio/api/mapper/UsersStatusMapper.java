package com.spaco_apoio.api.mapper;

import com.spaco_apoio.api.model.UsersStatus;
import com.spaco_apoio.api.rest.RestUsersStatus;
import com.spaco_apoio.api.utility.UtilSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface UsersStatusMapper {

    UsersStatusMapper INSTANCE = Mappers.getMapper(UsersStatusMapper.class);

    RestUsersStatus convertToRest(UsersStatus usersStatus);

    UsersStatus convertToModel(RestUsersStatus restUsersStatus);
}
