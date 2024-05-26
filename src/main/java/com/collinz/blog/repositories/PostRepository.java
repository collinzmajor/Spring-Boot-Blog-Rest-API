package com.collinz.blog.repositories;

import com.collinz.blog.models.Post;
import com.collinz.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByUserAndTitle(User user, String title);

    List<Post> findByUser(User user);

    Optional<Post> findByUserAndId(User user, Long id);

    boolean existsByUserAndTitleAndIdNot(User user, String title, Long id);
}
