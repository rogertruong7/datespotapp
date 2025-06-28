package com.datespot.user;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.datespot.reviews.Post;
import com.datespot.reviews.PostService;
import com.datespot.reviews.responses.PostResponse;
import com.datespot.reviews.responses.PostResponseFactory;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final PostService postService;
    private final PostResponseFactory postResponseFactory;

    @PatchMapping(path = "/password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal User user) {
        service.changePassword(request, user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/profile")
    public ResponseEntity<?> changeProfile(
            @RequestBody ChangeProfileRequest request,
            @AuthenticationPrincipal User user) {
        service.changeProfile(request, user);
        return ResponseEntity.ok().build();
    }

    /**
     * List a user’s posts.
     * GET /api/v1/users/{userId}/posts
     */
    @GetMapping(path = "/posts")
    public ResponseEntity<List<PostResponse>> listUserPosts(@PathVariable Integer userId) {
        List<Post> posts = postService.findByUser(userId);
        return ResponseEntity.ok(mapListPostToResponse(posts));
    }

    private List<PostResponse> mapListPostToResponse(List<Post> page) {
        List<PostResponse> responsePage = page.stream().map(post -> postResponseFactory.toPostResponse(post))
                .collect(Collectors.toList());
        return responsePage;
    }
}
