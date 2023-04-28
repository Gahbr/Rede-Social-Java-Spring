package com.sysmap.parrot.services;

import lombok.Data;

@Data
public class CreateUserRequest {
    public String username;
    public String email;
    public String password;

}

