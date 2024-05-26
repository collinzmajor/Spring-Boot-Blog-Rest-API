package com.collinz.blog.controllers;

import com.collinz.blog.exceptions.ObjectAlreadyExistsException;
import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.models.Post;
import com.collinz.blog.models.User;
import com.collinz.blog.requests.PostRequest;
import com.collinz.blog.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blog")
public class PostController {
    private final PostService service;

    @Autowired
    public PostController(PostService postService){
        this.service = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(service.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> findPostById(@RequestParam("id") Long id) throws ObjectNotFoundException {
        return ResponseEntity.ok(service.findPostById(id));
    }

    @GetMapping("/my/posts")
    public ResponseEntity<List<Post>> getMyPosts(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(service.getMyPosts(user));
    }

    @PostMapping("/my/posts")
    public ResponseEntity<String> savePost(@AuthenticationPrincipal User user, @RequestBody @Valid PostRequest request) throws ObjectAlreadyExistsException {
        service.save(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post successfully created!");
    }

    @PatchMapping("/my/posts/{id}")
    public ResponseEntity<String> updatePost(@AuthenticationPrincipal User user, @RequestParam("id") Long id, @RequestBody @Valid PostRequest request) throws ObjectAlreadyExistsException, ObjectNotFoundException {
        service.update(user, id, request);
        return ResponseEntity.ok("Post successfully updated!");
    }

    @DeleteMapping("/my/posts/{id}")
    public ResponseEntity<String> deletePost(@AuthenticationPrincipal User user, @RequestParam("id") Long id) throws ObjectNotFoundException {
        service.deleteById(user, id);
        return ResponseEntity.ok("Post successfully deleted!");
    }
}
