package com.spaco_apoio.api.mapper;

import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.utility.UtilSecurity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {UtilSecurity.class, UsersStatusMapper.class, UsersProfileMapper.class})
public interface UsersMapper {

    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    @Mappings({
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "profile", ignore = true),
            @Mapping(target = "profileId", qualifiedByName = "encryptId"),
            @Mapping(target = "statusId", qualifiedByName = "encryptId")
    })
    RestUsers convertToRest(Users users);

    @Mappings({
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "resetToken", ignore = true)
    })
    Users convertToModel(RestUsers restUsers);

    @Named(value = "encryptId")
    default String encryptId(Long id){
        return UtilSecurity.encryptId(id);
    }
}
