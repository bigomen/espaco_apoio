package com.spaco_apoio.api.controller.admin;

import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.rest.RestUsersProfile;
import com.spaco_apoio.api.rest.RestUsersStatus;
import com.spaco_apoio.api.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/admin/users")
public class UsersController {

    @Autowired
    private UsersService usersService;


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

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/createUserAdmin")
    public void createUserAdmin(@RequestBody RestUsers rest){
        usersService.createUserAdmin(rest);
    }
}
