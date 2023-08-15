package com.spaco_apoio.api.controller.admin;

import com.spaco_apoio.api.rest.RestResetPassword;
import com.spaco_apoio.api.rest.RestUsers;
import com.spaco_apoio.api.rest.RestUsersProfile;
import com.spaco_apoio.api.rest.RestUsersStatus;
import com.spaco_apoio.api.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/admin/users")
public class UsersController extends EnvController{

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

    //Test-only
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/listAll")
    public Collection<RestUsers> listAll(){
        return usersService.listAll();
    }

    //Test-only
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/createUserAdmin")
    public void createUserAdmin(@RequestBody RestUsers rest){
        usersService.createUserAdmin(rest);
    }

    //Test-only
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/createPassword")
    public void createPassword(@RequestBody HashMap<String, String> body){
        usersService.createPassword(body);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/create")
    public void create(@RequestBody RestUsers rest){
        usersService.create(rest, getEmailPropertiesCreate());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/update")
    public void update(@RequestBody RestUsers rest){
        usersService.update(rest);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/delete/{userId}")
    public void delete(@PathVariable String userId){
        usersService.delete(userId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/updateUserPassword")
    public void updateUserPassword(@RequestBody RestResetPassword rest){
        usersService.insertNewPassword(rest);
    }

}
