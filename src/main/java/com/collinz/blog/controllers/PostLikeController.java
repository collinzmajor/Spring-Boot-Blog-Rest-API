package com.collinz.blog.controllers;

import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.models.User;
import com.collinz.blog.requests.PostLikeRequest;
import com.collinz.blog.services.PostLikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/blog/my/likes")
public class PostLikeController {
    public final PostLikeService service;

    @Autowired
    public PostLikeController(PostLikeService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> saveLike(@AuthenticationPrincipal User user, @RequestBody @Valid PostLikeRequest request) throws ObjectNotFoundException {
        service.save(user, request);
        return ResponseEntity.ok("Like successfully saved!");
    }
}
