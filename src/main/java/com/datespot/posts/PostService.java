package com.datespot.posts;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datespot.posts.responses.PostCreatedResponse;
import com.datespot.posts.responses.PostResponseFactory;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;
    private PostResponseFactory postResponseFactory;

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

    public PostCreatedResponse create(PostRequest request) {
        Post post = Post.builder()
                .postId(request.getPostId())
                .authorId(request.getAuthorId())
                .reviewText(request.getReviewText())
                .location(request.getLocation())
                .isPublic(Boolean.TRUE.equals(request.getIsPublic()))
                .rating(request.getRating())
                .build();
        this.save(post);
        return postResponseFactory.toPostCreatedResponse(post);
    }

    // public Page<Post> findByUser(Integer authorId, Pageable pageable) {
    // return repository.findAllByAuthorId(authorId, pageable);
    // }
}
