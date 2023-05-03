package com.sysmap.parrot.controller;

import com.sysmap.parrot.dto.CreateLoginRequest;
import com.sysmap.parrot.dto.CreateUserRequest;
import com.sysmap.parrot.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request){
        var response = userService.createUser(request);
        return ResponseEntity.status(200).body(response);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody CreateLoginRequest request){
        var response = userService.loginUser(request);
        return ResponseEntity.status(200).body(response);
    }
}
