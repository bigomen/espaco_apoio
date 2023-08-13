package com.spaco_apoio.api.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.spaco_apoio.api.constants.Constants;
import com.spaco_apoio.api.model.Users;
import com.spaco_apoio.api.model.UsersProfile;
import com.spaco_apoio.api.model.UsersStatus;
import com.spaco_apoio.api.repository.UsersRepository;
import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.rest.RestUsersProfile;
import com.spaco_apoio.api.rest.RestUsersStatus;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
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

    public void createPassword(HashMap<String, String> body){
        sendEmail(body);
    }

    private static void sendEmail(HashMap<String, String> body){
        Email from = new Email(body.get("senderMail"));
        String subject = body.get("subject");
        Content content = new Content();
        content.setType("text/html");
        content.setValue(body.get("content"));
        content.setValue(content.getValue().replace("#usuario#", "Individuo"));
        content.setValue(content.getValue().replace("#token#", RandomString.make(50)));
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
