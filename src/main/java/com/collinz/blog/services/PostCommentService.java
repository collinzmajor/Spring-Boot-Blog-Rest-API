package com.collinz.blog.services;

import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.models.Post;
import com.collinz.blog.models.PostComment;
import com.collinz.blog.models.User;
import com.collinz.blog.repositories.PostCommentRepository;
import com.collinz.blog.repositories.PostRepository;
import com.collinz.blog.requests.PostCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostCommentService {
    private final PostRepository postRepository;
    private final PostCommentRepository repository;

    @Autowired
    public PostCommentService(PostRepository postRepository, PostCommentRepository postCommentRepository){
        this.postRepository = postRepository;
        this.repository = postCommentRepository;
    }

    public void save(User user, PostCommentRequest request) throws ObjectNotFoundException {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new ObjectNotFoundException("Post does not exist!"));

        PostComment comment = new PostComment();
        comment.setPost(post);
        comment.setBody(request.getBody());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);

        repository.save(comment);
    }

    @Transactional
    public void update(User user, Long id, PostCommentRequest request) throws ObjectNotFoundException {
        PostComment comment = repository.findByUserAndId(user, id).orElseThrow(() -> new ObjectNotFoundException("Comment not found!"));

        if(request.getBody() != null && !request.getBody().equals(comment.getBody())){
            comment.setBody(request.getBody());
            comment.setUpdatedAt(LocalDateTime.now());
        }
    }

    public void delete(User user, Long id) throws ObjectNotFoundException {
        PostComment comment = repository.findByUserAndId(user, id).orElseThrow(() -> new ObjectNotFoundException("Comment not found!"));

        repository.deleteById(id);
    }
}
