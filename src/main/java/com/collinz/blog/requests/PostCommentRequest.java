package com.collinz.blog.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class PostCommentRequest {
    @Min(0)
    private Long postId;
    @NotEmpty
    private String body;

    public PostCommentRequest() {
    }

    public PostCommentRequest(Long postId, String body) {
        this.postId = postId;
        this.body = body;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
