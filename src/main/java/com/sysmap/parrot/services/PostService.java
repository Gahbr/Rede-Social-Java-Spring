package com.sysmap.parrot.services;

import com.amazonaws.services.pi.model.NotAuthorizedException;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.repository.PostRepository;
import com.sysmap.parrot.entities.Like;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.dto.CreateLikePostRequest;
import com.sysmap.parrot.dto.CreatePostRequest;
import com.sysmap.parrot.services.fileUpload.IFileUploadService;
import com.sysmap.parrot.services.security.IJWTService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private IJWTService _jwtService;
    private IFileUploadService _fileUploadService;
    private UserService _userService;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public Optional<Post> getPostById(String id){
        return postRepository.findById(id);
    }

    public Post createPost(MultipartFile photo, String userId, String title, String description){

        if(!Objects.equals(userId, _jwtService.getLoggedUserId())){
            throw new NotAuthorizedException("NÃO PODE CRIAR POST COM ID DIFERENTE!");
        }else {
            Optional<User> optionalUser = _userService.getUserById(userId);
            User user = optionalUser.get();
            Random random = new Random();
            var randomNumber =   Integer.toString(random.nextInt(1000));
            var randomString ="";
            for (int i = 0; i < 3; i++) {
                char c = (char) (random.nextInt(26) + 'a');
                randomString += c;
            }

            var fileName = "Post/"+ randomNumber + randomString + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+ 1);

            String photoUri = _fileUploadService.upload(photo, fileName);
            var post = new Post();
            post.setUserId(userId);
            post.setTitle(title);
            post.setDescription(description);
            post.setImage(photoUri);
            post.setCreated(LocalDateTime.now());
            post.setLikes(new ArrayList<>());
            post.setComments(new ArrayList<>());

            return postRepository.save(post);
        }
    }

    public Post editPost(String id, CreatePostRequest request) {
        Optional<Post> optionalPost = postRepository.findById(id);
        Optional<User> optionalUser = _userService.getUserById(request.getUserId());

        if (optionalPost.isEmpty() || optionalUser.isEmpty()) {
            throw new NoSuchElementException("Post ou usuário não encontrado ");
        }

        Post post = optionalPost.get();

        if(!Objects.equals(post.getUserId(), _jwtService.getLoggedUserId())) {
            throw new NotAuthorizedException("Você não é o autor do post!");
        }

        if(request.getDescription() != null && !request.getDescription().isEmpty()) post.setDescription(request.getDescription());
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
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new NoSuchElementException("Post não encontrado com esse id: " + postId);
        }
        Post post = optionalPost.get();

        if (!Objects.equals(post.getUserId(), _jwtService.getLoggedUserId())) {
            throw new NotAuthorizedException("Você não é o autor do post!");
        }

        postRepository.deleteById(postId);
        return "Post deletado com sucesso";
    }
}
