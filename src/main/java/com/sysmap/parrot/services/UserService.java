package com.sysmap.parrot.services;

import com.sysmap.parrot.entities.Followers;
import com.sysmap.parrot.repository.UserRepository;
import com.sysmap.parrot.entities.Following;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.dto.CreateFollowUserRequest;
import com.sysmap.parrot.dto.CreateUserRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
        user.setFollowing(new ArrayList<>());
        user.setFollowers(new ArrayList<>());

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

    public String followUser(String id, @NotNull CreateFollowUserRequest request) {
        String userId = request.getUserId();
        //Usuario a ser seguido
        Optional<User>  OptFollowedUser = userRepository.findById(id);
        User followedUser = OptFollowedUser.get();

        //Usuario que vai seguir
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();



        if(userId.isBlank()){
            return "Insira um userID";
        }
        else {
            Following follow = new Following(Collections.singletonList(id));
            Followers follower = new Followers(Collections.singletonList(userId));

            user.getFollowing().add(follow);
            followedUser.getFollowers().add(follower);
            userRepository.save(user);
            userRepository.save(followedUser);

            return "Você seguiu o usuário " + followedUser.getUsername();
        }
    }

    public void deleteUser(String id) throws ChangeSetPersister.NotFoundException {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        System.out.println("Usuário deletado com sucesso!");
    }

}

