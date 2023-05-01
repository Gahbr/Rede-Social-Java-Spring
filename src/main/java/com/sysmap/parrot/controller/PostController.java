package com.sysmap.parrot.controller;

import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.dto.CreateLikePostRequest;
import com.sysmap.parrot.dto.CreatePostRequest;
import com.sysmap.parrot.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> fetchAllPosts(){
        var response = postService.getAllPosts();
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Post>> fetchPostById(@PathVariable String id){
        var response = postService.getPostById(id);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody CreatePostRequest request){
        Post response = postService.createPost(request);
        return ResponseEntity.status(200).body(response);
    }
    @PostMapping("/posts/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable String id, @RequestBody CreateLikePostRequest request){
       var response = postService.likePost(id, request);
        return ResponseEntity.status(200).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable String id, @RequestBody CreatePostRequest request){
        Post response = postService.editPost(id, request);
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> createUser(@PathVariable String id){
        var response = postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
