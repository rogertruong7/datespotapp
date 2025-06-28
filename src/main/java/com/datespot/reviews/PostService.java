package com.datespot.reviews;

import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.datespot.reviews.responses.PostCreatedResponse;
import com.datespot.reviews.responses.PostResponse;
import com.datespot.reviews.responses.PostResponseFactory;
import com.datespot.user.User;
import com.datespot.user.UserRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostResponseFactory postResponseFactory;
    private final UserRepository userRepository;

    public void save(Post post) {
        postRepository.save(post);
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public PostResponse findById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post does not exist"));
        if (!post.getIsPublic()) {
            throw new IllegalStateException("Post is not public");
        }
        return postResponseFactory.toPostResponse(post);
    }

    public PostCreatedResponse create(PostRequest request, User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User does not exist"));

        Rating rating = Rating.builder()
                .score(request.getRating()) // or however you get it
                .build();

        Post post = Post.builder()
                // postId should not be set manually unless you're doing an update
                .authorId(user.getId())
                .reviewTitle(request.getReviewTitle())
                .reviewText(request.getReviewText())
                .location(request.getLocation())
                .isPublic(Boolean.TRUE.equals(request.getIsPublic()))
                .rating(rating)
                .createDate(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build();
        System.out.println(post);
        this.save(post);
        managedUser.addPost(post.getPostId());
        // Hibernate only persists changes if you explicitly save or the transaction commits.
        userRepository.save(managedUser);
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
    // return postRepository.findAllByAuthorId(authorId, pageable);
    // }
}
