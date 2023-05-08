package com.sysmap.parrot.services;

import com.sysmap.parrot.dto.AuthenticateResponse;
import com.sysmap.parrot.dto.CreateLoginRequest;
import com.sysmap.parrot.entities.Followers;
import com.sysmap.parrot.repository.UserRepository;
import com.sysmap.parrot.entities.Following;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.dto.CreateUserRequest;
import com.sysmap.parrot.services.broker.IEventService;
import com.sysmap.parrot.services.fileUpload.IFileUploadService;
import com.sysmap.parrot.services.security.IJWTService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private IJWTService _jwtService;
    private IFileUploadService _fileUploadService;
    @Autowired
    private IEventService _eventService;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserById(String userId){
        return userRepository.findById(userId);
    }

    public String createUser(CreateUserRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        var user = new User(request.getUsername(), request.getEmail(), encoder.encode(request.getPassword()));
        user.setFollowing(new ArrayList<>());
        user.setFollowers(new ArrayList<>());
        user.setAvatar("");
        user.setDescription("");

        if(request.getUsername().isBlank() || request.getEmail().isBlank() || request.getPassword().isBlank()){
            return "Não pode ter campo vazio!";
        }else if ( userRepository.findByUsername(request.getUsername()).isPresent() ||userRepository.findByEmail(request.getEmail()).isPresent() ) {
            throw new IllegalArgumentException("Um usuário com esse username ou e-mail já existe");
        }else {
            userRepository.save(user);
            _eventService.send("Um usuário foi criado! ID:"+user.getId());
            return "Usuário criado com sucesso!";
        }
    }

    public AuthenticateResponse login(CreateLoginRequest request){
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        var response = new AuthenticateResponse();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if( encoder.matches(request.getPassword(), user.getPassword())){
                var token = _jwtService.generateToken(user.getId());
                response.setUserId(user.getId());
                response.setToken(token);
                return response;
            }
            else throw new RuntimeException("Senha incorreta!");
        }else {
            throw new NoSuchElementException("Usuário não encontrado com o email: " + request.getEmail());
        }
    }

    public User editUser(String id, CreateUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        User user = optionalUser.get();

        if(request.getUsername() != null && !request.getUsername().isEmpty()) user.setUsername(request.getUsername());
        if(request.getEmail() != null && !request.getEmail().isEmpty()) user.setEmail(request.getEmail());
        if(request.getDescription() != null && !request.getDescription().isEmpty()) user.setDescription(request.getDescription());
        if(request.getPassword() != null && !request.getPassword().isEmpty()) user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);

        return user;
    }

    public String followUser(String id) {
        String userId = _jwtService.getLoggedUserId();
        //Usuario a ser seguido
        Optional<User>  OptFollowedUser = userRepository.findById(id);
        User followedUser = OptFollowedUser.get();

        //Usuario que vai seguir
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        boolean isAlreadyFollowing = user.getFollowing().stream()
                .flatMap(f -> f.getUsers().stream())
                .anyMatch(id::equals);

        if(userId.isBlank()) return "Insira um userID";
        else if(isAlreadyFollowing) return "Você já segue esse usuário!";
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
        String userId = _jwtService.getLoggedUserId();
        Optional<User> user = userRepository.findById(id);
        if(!Objects.equals(userId, id)){
            throw new IllegalArgumentException("Você não pode deletar um usuário diferente do seu ID!");
        }
        userRepository.deleteById(id);
        System.out.println("Usuário deletado com sucesso!");
    }

    public String uploadAvatar(MultipartFile photo) throws Exception {
        String id = _jwtService.getLoggedUserId();
        String photoUri = "";
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.get();
        var fileName = user.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+ 1);

        try{
            photoUri = _fileUploadService.upload(photo, fileName);

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        user.setAvatar(photoUri);
        userRepository.save(user);
        return "avatar: " + photoUri;
    }
}

