 package com.sysmap.parrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


 @SpringBootApplication
public class ParrotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParrotApplication.class, args);
	}

// //Inserir dado manualmente STUDENT
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

	 //INSERINDO USUARIO MANUALMENTE
//	@Bean
//	 CommandLineRunner runner2(UserRepository repository ){
//		 String email = "teste@teste.com";
//
//		 return args -> {
//			 User user = new User("UserTeste",email,"senha");
//			 repository.insert(user);
//		 };
//	 }

//	 //INSERINDO POST MANUALMENTE
//	 @Bean
//	 CommandLineRunner runner(PostRepository repository ){
//
//		 return args -> {
//			 Post post = new Post();
//			 post.setUserId("123123");
//			 post.setContent("Ola´mundo");
//			 post.setLikes(Arrays.asList(new Like(Collections.singletonList("644a8eb865940f60a469f182")), new Like(Collections.singletonList("644b0281d689aa399b5eac8a"))));
//			 post.setComments(Arrays.asList(
//					 new Comment("644a8eb865940f60a469f182", "Naruto kun", Arrays.asList(new Like(Collections.singletonList("123123")), new Like(Collections.singletonList("456455")))),
//					 new Comment("644b0281d689aa399b5eac8a", "OIOIOIOI", null)
//			 ));
//			 repository.save(post);
//		 };
//	 }

}
