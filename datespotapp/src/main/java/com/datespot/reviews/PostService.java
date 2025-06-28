package com.datespot.reviews;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datespot.reviews.responses.PostCreatedResponse;
import com.datespot.reviews.responses.PostResponse;
import com.datespot.reviews.responses.PostResponseFactory;
import com.datespot.user.User;
import com.datespot.user.UserRepository;

/**
 * Service class for handling business logic related to posts.
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostResponseFactory postResponseFactory;
    private final UserRepository userRepository;

    /**
     * Persists a new or updated Post entity.
     *
     * @param post The post to save.
     */
    public void save(Post post) {
        postRepository.save(post);
    }

    /**
     * Retrieves all public posts in a paginated format for global feed display.
     *
     * @param pageable The pagination and sorting information.
     * @return A page of public posts.
     */
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findByIsPublicTrue(pageable);
    }

    /**
     * Retrieves a specific public post by its ID.
     *
     * @param postId The ID of the post.
     * @return The post as a PostResponse object.
     * @throws NoSuchElementException if the post does not exist.
     * @throws IllegalStateException  if the post is not public.
     */
    public PostResponse findById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post does not exist"));
        if (!post.getIsPublic()) {
            throw new IllegalStateException("Post is not public");
        }
        return postResponseFactory.toPostResponse(post);
    }

    /**
     * Creates and persists a new post based on the provided request and user.
     *
     * @param request The post creation request data.
     * @param user    The authenticated user creating the post.
     * @return A response containing the created post's summary.
     * @throws NoSuchElementException if the user does not exist.
     */
    public PostCreatedResponse create(PostRequest request, User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User does not exist"));

        Rating rating = Rating.builder()
                .score(request.getRating())
                .build();

        Post post = Post.builder()
                .authorId(user.getId())
                .reviewTitle(request.getReviewTitle())
                .reviewText(request.getReviewText())
                .location(request.getLocation())
                .isPublic(Boolean.TRUE.equals(request.getIsPublic()))
                .rating(rating)
                .createDate(LocalDateTime.now())
                .lastModified(LocalDateTime.now())
                .build();

        this.save(post);
        managedUser.addPost(post.getPostId());
        userRepository.save(managedUser);
        return postResponseFactory.toPostCreatedResponse(post);
    }

    /**
     * Updates an existing post with the given changes, provided the user is the
     * author.
     * Only non-null fields in the request will be applied (partial update).
     *
     * @param postId  The ID of the post to update.
     * @param request The changes to apply.
     * @param user    The currently authenticated user.
     * @throws ResponseStatusException if the post is not found or the user is not
     *                                 the author.
     */
    public void update(Integer postId, EditPostRequest request, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        if (!post.getAuthorId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this post");
        }

        if (request.getReviewTitle() != null) {
            post.setReviewTitle(request.getReviewTitle());
        }
        if (request.getReviewText() != null) {
            post.setReviewText(request.getReviewText());
        }
        if (request.getIsPublic() != null) {
            post.setIsPublic(request.getIsPublic());
        }
        if (request.getLocation() != null) {
            post.setLocation(request.getLocation());
        }

        post.setLastModified(LocalDateTime.now());
        postRepository.save(post);
    }

    /**
     * Deletes a post by its ID.
     * 
     * @param postId The ID of the post to delete.
     * @throws UnsupportedOperationException currently not implemented.
     */
    public void delete(Integer postId) {
        postRepository.deleteById(postId);
    }
}
