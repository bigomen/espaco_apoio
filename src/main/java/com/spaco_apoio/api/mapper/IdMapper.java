package com.spaco_apoio.api.mapper;

import org.mapstruct.Named;

public interface IdMapper {

    @Named(value = "encryptId")
    default String encryptId(Long id){
        return UtilSecurity.encryptId(id);
    }

    @Named(value = "decryptId")
    default Long decryptId(String id){
        return UtilSecurity.decryptId(id);
    }
}
