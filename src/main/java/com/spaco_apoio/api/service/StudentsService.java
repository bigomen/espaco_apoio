package com.spaco_apoio.api.service;

import com.spaco_apoio.api.constants.Constants;
import com.spaco_apoio.api.exceptions.InvalidData;
import com.spaco_apoio.api.exceptions.UniqueException;
import com.spaco_apoio.api.model.Students;
import com.spaco_apoio.api.model.UsersProfile;
import com.spaco_apoio.api.model.UsersStatus;
import com.spaco_apoio.api.repository.StudentsRepository;
import com.spaco_apoio.api.repository.UsersRepository;
import com.spaco_apoio.api.rest.RestStudents;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class StudentsService {

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public void create(RestStudents rest, HashMap<String, String> props){
        if(rest.getBirthDate().isAfter(LocalDate.now())) throw new InvalidData("Data de nascimento inválida!");
        if(Objects.nonNull(rest.getUser().getEndDate())){
            if(rest.getUser().getStartDate().isAfter(rest.getUser().getEndDate())) throw new InvalidData("Data inicio posterior a data fim");
        }
        if(usersRepository.existsByEmail(rest.getUser().getEmail())){
            throw new UniqueException(rest.getUser().getEmail());
        }
        if(usersRepository.existsByCpf(rest.getUser().getCpf())){
            throw new UniqueException(rest.getUser().getCpf());
        }
        rest.getUser().setStartDate(LocalDate.now());
        Students model = rest.restToModel();
        model.getUser().setStatusId(Constants.USER_STATUS_INACTIVE);
        model.getUser().setProfileId(Constants.USER_PROFILE_STUDENT);
        model.getUser().setResetToken(RandomString.make(50));
        studentsRepository.save(model);
        props.put("token", model.getUser().getResetToken());
        props.put("username", model.getUser().getName());
        props.put("email", model.getUser().getEmail());

        UsersService.sendEmail(props);

    }
}
