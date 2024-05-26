package com.collinz.blog.services;

import com.collinz.blog.exceptions.ObjectAlreadyExistsException;
import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.models.Post;
import com.collinz.blog.models.User;
import com.collinz.blog.repositories.PostRepository;
import com.collinz.blog.requests.PostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository postRepository){
        this.repository = postRepository;
    }

    public List<Post> getAllPosts(){
        return repository.findAll();
    }

    public List<Post> getMyPosts(User user){
        return repository.findByUser(user);
    }

    public Post findPostById(Long id) throws ObjectNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Post not found!"));
    }

    public void save(User user, PostRequest postRequest) throws ObjectAlreadyExistsException {
        //search by user and title
        if (repository.existsByUserAndTitle(user, postRequest.getTitle())){
            throw new ObjectAlreadyExistsException("A post with the same title already exists!");
        }

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setBody(postRequest.getBody());
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);
        repository.save(post);
    }

    @Transactional
    public void update(User user, Long id, PostRequest postRequest) throws ObjectAlreadyExistsException, ObjectNotFoundException {
        //check if post exists
        Post post = repository.findByUserAndId(user, id).orElseThrow(() -> new ObjectNotFoundException("Post does not exist!"));
        boolean updated = false;

        if(postRequest.getTitle() != null && !post.getTitle().equals(postRequest.getTitle())){
            //make sure this new title doesn't conflict with others
            if (repository.existsByUserAndTitleAndIdNot(user, postRequest.getTitle(), id)){
                throw new ObjectAlreadyExistsException("A post with the same title already exists!");
            }

            post.setTitle(postRequest.getTitle());
            updated = true;
        }

        if (postRequest.getBody() != null && !postRequest.getBody().equals(post.getBody())){
            post.setBody(postRequest.getBody());
            updated = true;
        }

        if (updated){
            post.setUpdatedAt(LocalDateTime.now());
        }
    }

    public void deleteById(User user, Long id) throws ObjectNotFoundException {
        Post post = repository.findByUserAndId(user, id).orElseThrow(() -> new ObjectNotFoundException("Post does not exist!"));
        repository.deleteById(id);
    }
}
