package com.collinz.blog.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;

public class PostRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private String body;

    public PostRequest() {
    }

    public PostRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
