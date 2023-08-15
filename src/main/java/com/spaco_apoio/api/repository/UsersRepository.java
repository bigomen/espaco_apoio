package com.spaco_apoio.api.repository;

import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.model.UsersProfile;
import com.spaco_apoio.api.model.UsersStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

    @Query("select CASE WHEN count(u.id) > 0 THEN true ELSE false END from Users u where u.email = :email and u.id <> :id")
    Boolean existsByEmailUpdate(String email, Long id);

    @Query("select CASE WHEN count(u.id) > 0 THEN true ELSE false END from Users u where u.cpf = :cpf and u.id <> :id")
    Boolean existsByCpfUpdate(String cpf, Long id);

    @Query("select new Users(u.id, u.resetToken) from Users u where u.email = :email")
    Users getUserByEmail(String email);

    @Query("select up from UsersProfile up")
    Collection<UsersProfile> getProfileList();

    @Query("select us from UsersStatus us")
    Collection<UsersStatus> getStatusList();

    @Modifying
    @Query("update Users u set u.password = :password, u.resetToken = null, u.statusId = :status where u.id = :id")
    void updateUserPassword(String password, Long id, Long status);

    @Query("select new Users(u.startDate, u.endDate, u.password) from Users u where u.id = :id")
    Users getUserDataUpdate(Long id);

    @Query("select new Users(u.email, u.endDate, u.statusId, p.description, u.password) from Users u join u.profile p where u.email = :email")
    Optional<Users> findByEmail(String email);
}
