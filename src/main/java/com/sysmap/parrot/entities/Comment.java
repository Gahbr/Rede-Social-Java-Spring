package com.sysmap.parrot.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Comment {
    private String userId;
    private String content;
    private List<Like> likes;
}
