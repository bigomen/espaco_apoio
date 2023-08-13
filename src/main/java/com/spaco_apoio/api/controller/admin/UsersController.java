package com.spaco_apoio.api.controller.admin;

import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.rest.RestUsersProfile;
import com.spaco_apoio.api.rest.RestUsersStatus;
import com.spaco_apoio.api.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/admin/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private Environment env;


    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/profileList")
    public Collection<RestUsersProfile> getProfileList(){
        return usersService.getProfileList();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/statusList")
    public Collection<RestUsersStatus> getStatusList(){
        return usersService.getStatusList();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/listAll")
    public Collection<RestUsers> listAll(){
        return usersService.listAll();
    }

    //Test-only
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/createUserAdmin")
    public void createUserAdmin(@RequestBody RestUsers rest){
        usersService.createUserAdmin(rest);
    }

    //Test-only
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/createPassword")
    public void createPassword(@RequestBody HashMap<String, String> body){
        body.put("senderMail", env.getProperty("sendgrid.email"));
        body.put("subject", env.getProperty("sendgrid.subject.create"));
        body.put("content", env.getProperty("sendgrid.content.create"));
        body.put("key", env.getProperty("sendgrid.key"));
        usersService.createPassword(body);
    }
}
