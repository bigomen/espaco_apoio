package com.spaco_apoio.api.controller.admin;

import com.spaco_apoio.api.rest.RestStudents;
import com.spaco_apoio.api.rest.params.StudentsParams;
import com.spaco_apoio.api.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/students")
public class StudentsController{

    @Autowired
    private StudentsService studentsService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/create")
    public void create(@RequestBody RestStudents rest){
        studentsService.create(rest);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/update")
    public void update(@RequestBody RestStudents rest){
        studentsService.update(rest);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/list")
    public Page<RestStudents> list(StudentsParams params, Pageable pageable){
        return studentsService.list(params, pageable);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/details/{id}")
    public RestStudents details(@PathVariable String id){
        return studentsService.details(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable String id){
        studentsService.delete(id);
    }
}
