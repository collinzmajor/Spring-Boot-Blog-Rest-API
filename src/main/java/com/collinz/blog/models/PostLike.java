package com.collinz.blog.models;

import com.collinz.blog.models.ids.PostLikeId;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PostLike {
    @EmbeddedId
    private PostLikeId id;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private Post post;

    public PostLike() {
    }

    public PostLike(PostLikeId id, LocalDateTime createdAt, User user, Post post) {
        this.id = id;
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
    }

    public PostLikeId getId() {
        return id;
    }

    public void setId(PostLikeId id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "PostLike{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", user=" + user +
                ", post=" + post +
                '}';
    }
}
