package com.sysmap.parrot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateLikePostRequest {
    @NotNull
    private String userId;

}