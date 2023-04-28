 package com.sysmap.parrot;

import com.sysmap.parrot.data.StudentRepository;
import com.sysmap.parrot.data.UserRepository;
import com.sysmap.parrot.entities.Address;
import com.sysmap.parrot.entities.Gender;
import com.sysmap.parrot.entities.Student;
import com.sysmap.parrot.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

 @SpringBootApplication
public class ParrotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParrotApplication.class, args);
	}

// //Inserir dado manualmente
//	@Bean
//	 CommandLineRunner runner(StudentRepository repository ){
//		String email = "birb@birb.com";
//
//		return args -> {
//			Address address = new Address("bostil","bostilia","72666");
//			Student student = new Student("Lorde", "Passaro", email, Gender.FEMALE,
//					address, List.of("Computer Science"), BigDecimal.TEN, LocalDateTime.now());
//
//			//query customizada
//			repository.findStudentByEmail(email).ifPresentOrElse( s -> {
//				System.out.println(s + "existe!");
//			}, ()-> {
//				System.out.println("Inserindo aluno: "+ student);
//				repository.insert(student);
//			});
//		};
//	}

//	@Bean
//	 CommandLineRunner runner2(UserRepository repository ){
//		 String email = "teste@teste.com";
//
//		 return args -> {
//			 User user = new User("UserTeste",email,"senha");
//			 repository.insert(user);
//		 };
//	 }
}
