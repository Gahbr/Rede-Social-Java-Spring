package com.sysmap.parrot.services;

import com.sysmap.parrot.entities.Student;
import com.sysmap.parrot.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents(){
       return studentRepository.findAll();
    }

    public Optional<Student> getStudentByEmail(String email){
        return studentRepository.findStudentByEmail(email);
    }
}
