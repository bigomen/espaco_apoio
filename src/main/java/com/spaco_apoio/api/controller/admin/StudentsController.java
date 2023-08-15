package com.spaco_apoio.api.controller.admin;

import com.spaco_apoio.api.rest.RestStudents;
import com.spaco_apoio.api.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/students")
public class StudentsController extends EnvController{

    @Autowired
    private StudentsService studentsService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/create")
    public void create(@RequestBody RestStudents rest){
        studentsService.create(rest, getEmailPropertiesCreate());
    }
}
