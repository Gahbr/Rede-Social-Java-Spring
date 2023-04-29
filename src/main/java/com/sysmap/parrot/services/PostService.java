package com.sysmap.parrot.services;

import com.sysmap.parrot.data.PostRepository;
import com.sysmap.parrot.entities.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public Optional<Post> getPostById(String id){
        return postRepository.findById(id);
    }

    public Post createPost(CreatePostRequest request){
        var post = new Post();
            post.setUserId(request.getUserId());
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
            post.setCreated(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post editPost(String id, CreatePostRequest request) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.get();

        if(request.getContent() != null && !request.getContent().isEmpty()) post.setContent(request.getContent());
        if(request.getTitle() != null && !request.getTitle().isEmpty()) post.setTitle(request.getTitle());
        postRepository.save(post);

        return post;
    }
    public String deletePost(String postId){
        Optional<Post> post = postRepository.findById(postId);
        postRepository.deleteById(postId);

        return "Post deletado com sucesso";
    }
}
