package com.sysmap.parrot.dto;

import lombok.Data;

@Data
public class CreateCommentRequest {
    private String userId;
    private String content;
}
