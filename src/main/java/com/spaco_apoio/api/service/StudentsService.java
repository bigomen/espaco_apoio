package com.spaco_apoio.api.service;

import com.spaco_apoio.api.constants.Constants;
import com.spaco_apoio.api.exceptions.InvalidData;
import com.spaco_apoio.api.exceptions.NotFoundException;
import com.spaco_apoio.api.exceptions.UniqueException;
import com.spaco_apoio.api.model.Students;
import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.repository.StudentsRepository;
import com.spaco_apoio.api.repository.UsersRepository;
import com.spaco_apoio.api.rest.RestStudents;
import com.spaco_apoio.api.rest.params.StudentsParams;
import com.spaco_apoio.api.utility.UtilSecurity;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class StudentsService {

    @Autowired
    private StudentsRepository studentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Page<RestStudents> list(StudentsParams params, Pageable pageable){
        Page<Students> students = studentsRepository.list(params, pageable);
        return students.map(Students::modelToRest);
    }

    public RestStudents details(String id){
        Students student =  studentsRepository.details(UtilSecurity.decryptId(id));
        if(Objects.nonNull(student)) return student.modelToRest();
        throw new NotFoundException("Estudante");
    }

    public void create(RestStudents rest){
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

        UsersService.createPasswordMail(model.getUser());

    }

    public void update(RestStudents rest){
        if(rest.getBirthDate().isAfter(LocalDate.now())) throw new InvalidData("Data de nascimento inválida!");
        if(Objects.nonNull(rest.getUser().getEndDate())){
            if(rest.getUser().getStartDate().isAfter(rest.getUser().getEndDate())) throw new InvalidData("Data inicio posterior a data fim");
        }
        Long id = UtilSecurity.decryptId(rest.getId());
        if(!studentsRepository.existsById(id)) throw new NotFoundException("Estudante");
        if(usersRepository.existsByEmailUpdate(rest.getUser().getEmail(), id)){
            throw new UniqueException(rest.getUser().getEmail());
        }
        if(usersRepository.existsByCpfUpdate(rest.getUser().getCpf(), id)) throw new UniqueException(rest.getUser().getCpf());
        Users userDB = usersRepository.getUserDataUpdate(id);
        Students model = rest.restToModel();
        model.getUser().setStartDate(userDB.getStartDate());
        model.getUser().setEndDate(userDB.getEndDate());
        model.getUser().setPassword(userDB.getPassword());
        model.getUser().setProfileId(Constants.USER_PROFILE_STUDENT);
        model.getUser().setId(id);
        studentsRepository.save(model);
    }

    public void delete(String id) {
        Long studentId = UtilSecurity.decryptId(id);
        if (!studentsRepository.existsById(studentId)) {
            throw new NotFoundException("Estudante");
        }
        studentsRepository.deleteById(studentId);
    }
}
