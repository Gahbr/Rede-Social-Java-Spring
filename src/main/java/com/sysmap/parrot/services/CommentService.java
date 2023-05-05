package com.sysmap.parrot.services;

import com.amazonaws.services.pi.model.NotAuthorizedException;
import com.sysmap.parrot.dto.CreateCommentRequest;
import com.sysmap.parrot.entities.Comment;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.repository.PostRepository;
import com.sysmap.parrot.entities.Post;
import com.sysmap.parrot.services.security.IJWTService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class CommentService {
    private final PostRepository postRepository;
    private IJWTService _jwtService;
    private UserService _userService;

    public List<Comment> getComments(String postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return post.getComments();
        } else throw new NoSuchElementException("Não foi possível encontrar o post com o ID: " + postId);
    }

    public Comment createComment(String postId, CreateCommentRequest request) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = _userService.getUserById(request.getUserId());

        if (optionalPost.isEmpty() || optionalUser.isEmpty()) {
            throw new NoSuchElementException("Post ou usuário não encontrado ");
        }

        if(!Objects.equals(request.getUserId(), _jwtService.getLoggedUserId())) {
            throw new NotAuthorizedException("Você só pode criar um comentário com o seu id!");
        }

        Post post = optionalPost.get();

        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setUserId(request.getUserId());
        comment.setContent(request.getContent());
        comment.setLikes(new ArrayList<>());

        post.getComments().add(comment);
        postRepository.save(post);

        return comment;
    }

    public Comment editComment(String postId, String commentId, CreateCommentRequest request) {
        if(!Objects.equals(request.getUserId(), _jwtService.getLoggedUserId())) {
            throw new NotAuthorizedException("Você não é o autor do comentário!");
        }

        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()){
            throw new NoSuchElementException("Post não encontrado com o ID: " + postId);
        }

            Post post = optionalPost.get();
            List<Comment> comments = post.getComments();
            for (Comment comment : comments) {
                if (comment.getId().equals(commentId)) {
                    comment.setContent(request.getContent());
                    postRepository.save(post);
                    return comment;
                }
            }
            throw new NoSuchElementException("Comentário não encontrado com o ID: " + commentId);
    }

    public String deleteComment(String postId, String commentId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) {
            throw new NoSuchElementException("Post não encontrado com o ID: " + postId);
        }

        Post post = optionalPost.get();
        Optional<Comment> optionalComment = post.getComments().stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst();

        if (optionalComment.isEmpty()) {
            throw new NoSuchElementException("Comentário não encontrado com o ID: " + commentId);
        }

        Comment comment = optionalComment.get();
        post.getComments().remove(comment);
        postRepository.save(post);
        return "Comentário deletado!";
    }


}
