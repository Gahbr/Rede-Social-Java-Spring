package com.sysmap.parrot.services;

import com.sysmap.parrot.data.UserRepository;
import com.sysmap.parrot.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public String createUser(CreateUserRequest request){
        var user = new User(request.getUsername(), request.getEmail(), request.getPassword());

        if(request.getUsername().isBlank() || request.getEmail().isBlank() || request.getPassword().isBlank()){
            return "Não pode ter campo vazio!";
        }else {
            userRepository.save(user);
            return user.getId();
        }
    }

    public User editUser(String id, CreateUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();

        if(request.getUsername() != null && !request.getUsername().isEmpty()) user.setUsername(request.getUsername());
        if(request.getEmail() != null && !request.getEmail().isEmpty()) user.setEmail(request.getEmail());
        if(request.getPassword() != null && !request.getPassword().isEmpty()) user.setPassword(request.getPassword());
        userRepository.save(user);

        return user;
    }

    public void deleteUser(String id) throws ChangeSetPersister.NotFoundException {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        System.out.println("Usuário deletado com sucesso!");
    }

}
