package com.spaco_apoio.api.mapper;

import com.spaco_apoio.api.model.UsersProfile;
import com.spaco_apoio.api.rest.RestUsersProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class})
public interface UsersProfileMapper{

    UsersProfileMapper INSTANCE = Mappers.getMapper(UsersProfileMapper.class);

    RestUsersProfile convertToRest(UsersProfile usersProfile);

    UsersProfile convertToModel(RestUsersProfile restUsersProfile);
}
