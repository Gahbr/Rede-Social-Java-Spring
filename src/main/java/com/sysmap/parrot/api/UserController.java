package com.sysmap.parrot.api;

import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.services.CreateUserRequest;
import com.sysmap.parrot.services.UserService;
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
    public List<User> fetchAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{username}")
    public ResponseEntity<Optional<User>> fetchUserByUsername(@PathVariable String username){
        Optional<User> response = userService.getUserByUsername(username);
        return response.isPresent() ? ResponseEntity.status(200).body(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request){
        var response = userService.createUser(request);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editUser(@PathVariable String id,@RequestBody CreateUserRequest request){
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
