package com.datespot.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datespot.posts.responses.PostCreatedResponse;
import com.datespot.posts.responses.PostResponseFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    

    /**
     * List all posts (global feed), paginated.
     * GET /api/v1/posts?page=1&limit=20
     */
    @GetMapping
    public ResponseEntity<Page<Post>> listAllPosts(Pageable pageable) {
        Page<Post> page = postService.findAll(pageable);
        return ResponseEntity.ok(page);
    }



    /**
     * Get a single post (with comments).
     * GET /api/v1/posts/{postId}
     */
    @GetMapping(path = "/{postId}")
    public ResponseEntity<Post> getPost(
            @PathVariable Integer postId) {
        Post dto = postService.findById(postId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new review.
     * POST /api/v1/posts
     * Body: { "placeId", "rating", "reviewText", "isPublic": true }
     */
    @PostMapping
    public ResponseEntity<PostCreatedResponse> createPost(
            @RequestBody PostRequest request) {
        PostCreatedResponse created = postService.create(request);
        return ResponseEntity.status(201).body(created);
    }

    /**
     * Edit your review.
     * PATCH /api/v1/posts/{postId}
     */
    @PatchMapping(path = "/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable Integer postId,
            @RequestBody PostRequest request) {
        postService.update(postId, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete your review.
     * DELETE /api/v1/posts/{postId}
     */
    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer postId) {
        postService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
