package com.collinz.blog.requests;

import jakarta.validation.constraints.Min;

public class PostLikeRequest {
    @Min(0)
    private Long postId;

    public PostLikeRequest() {
    }

    public PostLikeRequest(Long postId) {
        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
