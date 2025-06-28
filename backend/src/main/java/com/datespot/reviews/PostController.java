package com.datespot.reviews;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.datespot.reviews.responses.PostCreatedResponse;
import com.datespot.reviews.responses.PostResponse;
import com.datespot.user.User;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
     * ✔️
     * Get a single post (with comments). No comments atm
     * GET /api/v1/posts/{postId}
     */
    @GetMapping(path = "/{postId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of post"),
            @ApiResponse(responseCode = "403", description = "Access denied, post is not public"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<PostResponse> getPost(
            @PathVariable Integer postId) {
        PostResponse dto = postService.findById(postId);
        return ResponseEntity.ok(dto);
    }

    /**
     * ✔️
     * Create a new review.
     * POST /api/v1/posts
     * Body: { "placeId", "rating", "reviewText", "isPublic": true }
     */

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of post"),
            @ApiResponse(responseCode = "403", description = "Access denied, post is not public"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    public ResponseEntity<PostCreatedResponse> createPost(
            @RequestBody PostRequest request,
            @AuthenticationPrincipal User user) {
        // Probably create the postId from here
        // authorId from frontend?
        PostCreatedResponse created = postService.create(request, user);
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
