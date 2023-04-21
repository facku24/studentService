package com.kunix.studentservice.controller;

import com.kunix.studentservice.repository.SubjectRepository;
import com.kunix.studentservice.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SubjectController {
    @Autowired
    private SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public String listAll(){
        List<Subject> subjectList = subjectRepository.findAll();
        return "success";
    }
}
