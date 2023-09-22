package com.spaco_apoio.api.mapper;

import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.utility.UtilSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class, UsersStatusMapper.class, UsersProfileMapper.class})
public interface UsersMapper extends IdMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mappings({
            @Mapping(target = "profileId", qualifiedByName = "encryptId"),
            @Mapping(target = "statusId", qualifiedByName = "encryptId")
    })
    RestUsers convertToRest(Users users);

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "resetToken", ignore = true),
            @Mapping(target = "profileId", qualifiedByName = "decryptId"),
            @Mapping(target = "statusId", qualifiedByName = "decryptId")
    })
    Users convertToModel(RestUsers restUsers);
}
