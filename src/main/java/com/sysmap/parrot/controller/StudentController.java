package com.sysmap.parrot.controller;

import com.sysmap.parrot.entities.Student;
import com.sysmap.parrot.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/students")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    public List<Student> fetchAllStudents(){
      return studentService.getAllStudents();
    }

    @GetMapping("/{email}")
    public Optional<Student> fetchStudentByEmail(@PathVariable String email){
        return studentService.getStudentByEmail(email);
    }
}
