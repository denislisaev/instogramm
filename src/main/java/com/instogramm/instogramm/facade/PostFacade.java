package com.instogramm.instogramm.facade;

import com.instogramm.instogramm.dto.PostDTO;
import com.instogramm.instogramm.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostFacade {
    public PostDTO postToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setId(post.getId());
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setCaption(post.getCaption());
        postDTO.setTitle(post.getTitle());
        postDTO.setLikes(post.getLikes());
        postDTO.setLikedUser(post.getLikedUsers());
        postDTO.setLocation(post.getLocation());

        return postDTO;
    }
}
