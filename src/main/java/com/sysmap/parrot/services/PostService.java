package com.sysmap.parrot.services;

import com.sysmap.parrot.repository.PostRepository;
import com.sysmap.parrot.entities.Like;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.dto.CreateLikePostRequest;
import com.sysmap.parrot.dto.CreatePostRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
            post.setLikes(new ArrayList<>());
            post.setComments(new ArrayList<>());
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

    public String likePost(String id, @NotNull CreateLikePostRequest request) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Post post = optionalPost.get();
        String userId = request.getUserId();
        boolean liked = false;

        for (Like like : post.getLikes()) {
            if (like.getUserId().toString().equals("["+userId+"]")) {
                liked = true;
                post.getLikes().remove(like);
                break;
            }
        }

        if(userId.isBlank()){
            return "Insira um userID";
        }
        else if (!liked) {
            Like like = new Like(Collections.singletonList(userId));
            post.getLikes().add(like);
            postRepository.save(post);
            return "Você deu like no post!";
        } else {
            postRepository.save(post);
            return "Você retirou o like nesse post.";
        }
    }

    public String deletePost(String postId){
        Optional<Post> post = postRepository.findById(postId);
        postRepository.deleteById(postId);

        return "Post deletado com sucesso";
    }
}
