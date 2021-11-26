package com.instogramm.instogramm.repository;

import com.instogramm.instogramm.entity.Post;
import com.instogramm.instogramm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreateDate();
    List<Post> findAllByUserOrderByCreateDate(User user);
    Optional<Post> findPostByIdAndUser(Long id, User user);
}
