package com.datespot.reviews;

import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datespot.reviews.responses.PostCreatedResponse;
import com.datespot.reviews.responses.PostResponseFactory;
import com.datespot.user.User;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private final PostResponseFactory postResponseFactory;

    public void save(Post post) {
        repository.save(post);
    }

    public Page<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Post findById(Integer postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public PostCreatedResponse create(PostRequest request, User user) {
        Rating rating = Rating.builder()
                .score(request.getRating()) // or however you get it
                .build();

        Post post = Post.builder()
                // postId should not be set manually unless you're doing an update
                .authorId(user.getId())
                .reviewText(request.getReviewText())
                .location(request.getLocation())
                .isPublic(Boolean.TRUE.equals(request.getIsPublic()))
                .rating(rating)
                .createDate(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build();
        System.out.println(post);
        this.save(post);
        return postResponseFactory.toPostCreatedResponse(post);
    }

    public void update(Integer postId, PostRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void delete(Integer postId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    // public Page<Post> findByUser(Integer authorId, Pageable pageable) {
    // return repository.findAllByAuthorId(authorId, pageable);
    // }
}
