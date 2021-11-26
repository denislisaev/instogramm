package com.instogramm.instogramm.service;

import com.instogramm.instogramm.dto.CommentDTO;
import com.instogramm.instogramm.entity.Comment;
import com.instogramm.instogramm.entity.Post;
import com.instogramm.instogramm.entity.User;
import com.instogramm.instogramm.exceptions.PostNotFoundException;
import com.instogramm.instogramm.repository.CommentRepository;
import com.instogramm.instogramm.repository.PostRepository;
import com.instogramm.instogramm.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getAllCommentsForPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFoundException("Post not found"));
        return commentRepository.findAllByPost(post);
    }

    public Comment saveComment (Long postId, CommentDTO commentDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFoundException("Post not found for username" + user.getUsername()));
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(commentDTO.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOG.info("Create new comment for post {}", post.getId());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

    public User getUserByPrincipal(Principal principal){
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
