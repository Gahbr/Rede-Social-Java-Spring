package com.sysmap.parrot.services;

import com.sysmap.parrot.data.PostRepository;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post createPost(CreatePostRequest request){
        var post = new Post();
            post.setUserId(request.getUserId());
            post.setTitle(request.getTitle());
            post.setContent(request.getContent());
        return postRepository.save(post);
    }

    public Optional<Post> getPostById(String id){
        return postRepository.findById(id);
    }

    public String deletePost(String postId){
        Optional<Post> post = postRepository.findById(postId);
        postRepository.deleteById(postId);

        return "Post deletado com sucesso";
    }
}
