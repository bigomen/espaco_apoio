package com.spaco_apoio.api.repository;

import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.model.UsersProfile;
import com.spaco_apoio.api.model.UsersStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

    @Query("select up from UsersProfile up")
    Collection<UsersProfile> getProfileList();

    @Query("select us from UsersStatus us")
    Collection<UsersStatus> getStatusList();
}
