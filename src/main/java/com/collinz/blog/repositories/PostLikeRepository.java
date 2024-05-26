package com.collinz.blog.repositories;

import com.collinz.blog.models.PostLike;
import com.collinz.blog.models.ids.PostLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
}
