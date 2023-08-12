package com.spaco_apoio.api.service;

import com.spaco_apoio.api.constants.Constants;
import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.model.UsersProfile;
import com.spaco_apoio.api.model.UsersStatus;
import com.spaco_apoio.api.repository.UsersRepository;
import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.rest.RestUsersProfile;
import com.spaco_apoio.api.rest.RestUsersStatus;
import com.spaco_apoio.api.utility.UtilSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Collection<RestUsersProfile> getProfileList(){
        Collection<UsersProfile> profiles = usersRepository.getProfileList();
        return profiles.stream().map(UsersProfile::modelToRest).collect(Collectors.toList());
    }

    public Collection<RestUsersStatus> getStatusList(){
        Collection<UsersStatus> status = usersRepository.getStatusList();
        return status.stream().map(UsersStatus::modelToRest).collect(Collectors.toList());
    }

    public Collection<RestUsers> listAll(){
        Collection<Users> users = (Collection<Users>) usersRepository.findAll();
        return users.stream().map(Users::modelToRest).collect(Collectors.toList());
    }

    public void createUserAdmin(RestUsers rest){
        rest.setStartDate(LocalDate.now());
        Users model = rest.restToModel();
        model.setStatusId(Constants.USER_STATUS_ACTIVE);
        model.setProfileId(Constants.USER_PROFILE_ADMIN);
        model.setPassword(bCryptPasswordEncoder.encode("@1234567"));
        usersRepository.save(model);
    }
}
