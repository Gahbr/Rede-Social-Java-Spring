package com.sysmap.parrot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFollowUserRequest {
    @NotNull
    private String userId;
}
