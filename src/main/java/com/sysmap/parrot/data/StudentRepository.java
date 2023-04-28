package com.sysmap.parrot.data;

import com.sysmap.parrot.entities.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository  extends MongoRepository<Student,String> {
    //query para achar aluno por email
    Optional<Student> findStudentByEmail(String email);

    //fazer query mongo
   //@Query("")
   //void test();

}
