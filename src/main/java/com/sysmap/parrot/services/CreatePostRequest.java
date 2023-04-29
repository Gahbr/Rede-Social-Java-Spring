package com.sysmap.parrot.services;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String userId;
    private String title;
    private String content;
}
