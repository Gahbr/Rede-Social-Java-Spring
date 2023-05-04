package com.sysmap.parrot.controller;

import com.sysmap.parrot.dto.CreateLoginRequest;
import com.sysmap.parrot.dto.CreateUserRequest;
import com.sysmap.parrot.services.UserService;
import com.sysmap.parrot.dto.AuthenticateResponse;
import com.sysmap.parrot.services.security.IJWTService;
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

    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse> loginUser(@Valid @RequestBody CreateLoginRequest request){
        var response = userService.login(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody CreateUserRequest request){
        var response = userService.createUser(request);
        return ResponseEntity.status(200).body(response);
    }

}