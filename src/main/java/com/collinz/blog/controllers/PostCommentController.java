package com.collinz.blog.controllers;

import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.models.User;
import com.collinz.blog.requests.PostCommentRequest;
import com.collinz.blog.services.PostCommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog/my/comments")
public class PostCommentController {
    private final PostCommentService service;

    @Autowired
    public PostCommentController(PostCommentService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> saveComment(@AuthenticationPrincipal User user, @RequestBody @Valid PostCommentRequest request) throws ObjectNotFoundException {
        service.save(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment successfully saved!");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateComment(@AuthenticationPrincipal User user, @RequestParam("id") Long id, @RequestBody @Valid PostCommentRequest request) throws ObjectNotFoundException {
        service.update(user, id, request);
        return ResponseEntity.ok("Comment successfully updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal User user, @RequestParam("id") Long id) throws ObjectNotFoundException {
        service.delete(user, id);
        return ResponseEntity.ok("Comment successfully deleted!");
    }
}
