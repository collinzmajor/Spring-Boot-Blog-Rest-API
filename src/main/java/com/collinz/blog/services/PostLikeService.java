package com.collinz.blog.services;

import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.models.Post;
import com.collinz.blog.models.PostLike;
import com.collinz.blog.models.User;
import com.collinz.blog.models.ids.PostLikeId;
import com.collinz.blog.repositories.PostLikeRepository;
import com.collinz.blog.repositories.PostRepository;
import com.collinz.blog.requests.PostLikeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostLikeService {
    private final PostRepository postRepository;
    private final PostLikeRepository repository;

    @Autowired
    public PostLikeService(PostLikeRepository repository, PostRepository postRepository){
        this.repository = repository;
        this.postRepository = postRepository;
    }

    public void save(User user, PostLikeRequest request) throws ObjectNotFoundException {
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new ObjectNotFoundException("Post does not exist!"));

        //make sure this user hasnt like this post before
        PostLikeId postLikeId = new PostLikeId(
                post.getId(),
                user.getId()
        );

        Optional<PostLike> optional = repository.findById(postLikeId);

        if (optional.isPresent()){
            //unlike
            repository.deleteById(postLikeId);
        }
        else{
            //like
            PostLike like = new PostLike();
            like.setPost(post);
            like.setCreatedAt(LocalDateTime.now());
            like.setUser(user);
            like.setId(
                    postLikeId
            );

            repository.save(like);
        }
    }
}
