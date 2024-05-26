package com.collinz.blog.repositories;

import com.collinz.blog.models.PostComment;
import com.collinz.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Optional<PostComment> findByUserAndId(User user, Long id);
}
