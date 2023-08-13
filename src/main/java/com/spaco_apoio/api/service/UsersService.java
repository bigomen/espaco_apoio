package com.spaco_apoio.api.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.spaco_apoio.api.constants.Constants;
import com.spaco_apoio.api.exceptions.InvalidData;
import com.spaco_apoio.api.exceptions.NotFoundException;
import com.spaco_apoio.api.exceptions.UniqueException;
import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.model.UsersProfile;
import com.spaco_apoio.api.model.UsersStatus;
import com.spaco_apoio.api.repository.UsersRepository;
import com.spaco_apoio.api.rest.RestResetPassword;
import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.rest.RestUsersProfile;
import com.spaco_apoio.api.rest.RestUsersStatus;
import com.spaco_apoio.api.utility.UtilSecurity;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
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
        Collection<RestUsers> rest = new ArrayList<>();
        for(Users u : users){
            u.setProfile(null);
            u.setStatus(null);
            RestUsers restUser = u.modelToRest();
            rest.add(restUser);
        }
        return rest;
    }

    public void createUserAdmin(RestUsers rest){
        rest.setStartDate(LocalDate.now());
        Users model = rest.restToModel();
        model.setStatusId(Constants.USER_STATUS_ACTIVE);
        model.setProfileId(Constants.USER_PROFILE_ADMIN);
        model.setPassword(bCryptPasswordEncoder.encode("@1234567"));
        usersRepository.save(model);
    }

    public void create(RestUsers rest, HashMap<String, String> props){
        if(usersRepository.existsByEmail(rest.getEmail())){
            throw new UniqueException(rest.getEmail());
        }
        if(usersRepository.existsByCpf(rest.getCpf())){
            throw new UniqueException(rest.getCpf());
        }
        rest.setStartDate(LocalDate.now());
        Users model = rest.restToModel();
        model.setStatus(new UsersStatus(Constants.USER_STATUS_INACTIVE));
        model.setResetToken(RandomString.make(50));
        usersRepository.save(model);
        props.put("token", model.getResetToken());
        props.put("username", model.getName());
        props.put("email", model.getEmail());
        sendEmail(props);
    }

    public void update(RestUsers rest){
        Long userId = UtilSecurity.decryptId(rest.getId());
        if(!usersRepository.existsById(userId)) throw new NotFoundException(rest.getName());
        if(usersRepository.existsByEmailUpdate(rest.getEmail(), userId)) throw new UniqueException(rest.getEmail());
        if(usersRepository.existsByCpfUpdate(rest.getCpf(), userId)) throw new UniqueException(rest.getCpf());

        Users userDB = usersRepository.getUserDataUpdate(userId);

        Users model = rest.restToModel();
        model.setStartDate(userDB.getStartDate());
        model.setEndDate(userDB.getEndDate());
        model.setPassword(userDB.getPassword());

        usersRepository.save(model);
    }

    public void delete(String id){
        Long userId = UtilSecurity.decryptId(id);
        if(!usersRepository.existsById(userId)){
            throw new NotFoundException("Usuário");
        }
        usersRepository.deleteById(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertNewPassword(RestResetPassword rest){
        Users user = usersRepository.getUserByEmail(rest.getEmail());
        if(Objects.isNull(user)){
            throw new NotFoundException(rest.getEmail());
        }
        if(!Objects.equals(user.getResetToken(), rest.getToken())){
            throw new InvalidData("Token");
        }
        String encriptedPassword = bCryptPasswordEncoder.encode(rest.getPassword());
        usersRepository.updateUserPassword(encriptedPassword, user.getId(), Constants.USER_STATUS_ACTIVE);
    }

    public void createPassword(HashMap<String, String> body){
        sendEmail(body);
    }

    private static void sendEmail(HashMap<String, String> body){
        Email from = new Email(body.get("senderMail"));
        String subject = body.get("subject");
        Content content = new Content();
        content.setType("text/html");
        content.setValue(body.get("content"));
        content.setValue(content.getValue().replace("#usuario#", body.get("username")));
        content.setValue(content.getValue().replace("#token#", body.get("token")));
        Email to = new Email(body.get("email"));
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(body.get("key"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
