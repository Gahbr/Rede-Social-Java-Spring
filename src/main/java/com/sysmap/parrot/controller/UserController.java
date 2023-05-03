package com.sysmap.parrot.controller;

import com.sysmap.parrot.dto.CreateLoginRequest;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.dto.CreateUserRequest;
import com.sysmap.parrot.services.UserService;
import com.sysmap.parrot.dto.CreateFollowUserRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profiles")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity <List<User>> fetchAllUsers(){
        List<User> response = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Optional<User>> fetchUserByUsername(@PathVariable String username){
        Optional<User> response = userService.getUserByUsername(username);
        return response.isPresent() ? ResponseEntity.status(200).body(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/{id}/follow")
    public ResponseEntity<String> followUser(@PathVariable String id, @RequestBody CreateFollowUserRequest request){
        var response = userService.followUser(id,request);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable String id,@RequestBody CreateUserRequest request){
        var response = userService.editUser(id,request);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Usuário com ID " + id + " foi deletado.");
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário com esse ID não foi encontrado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Houve algum erro ao deletar o usuário com o ID " + id);
        }
    }


}
