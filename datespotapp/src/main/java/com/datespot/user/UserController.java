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
import com.datespot.reviews.PostResponse;
import com.datespot.reviews.PostService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final PostService postService;

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
     * In the future have to implement, if the user is public/ if its private are you following
     */
    @GetMapping(path = "/{userId}/posts")
    public ResponseEntity<List<PostResponse>> listUserPosts(@PathVariable Integer userId) {
        List<Post> posts = postService.findByUser(userId);
        return ResponseEntity.ok(mapListPostToResponse(posts));
    }

    private List<PostResponse> mapListPostToResponse(List<Post> page) {
        List<PostResponse> responsePage = page.stream().map(
                PostResponse::DefaultResponse)
                .collect(Collectors.toList());
        return responsePage;
    }

    // Follow a user
    @PatchMapping("/{userId}/follow")
    public ResponseEntity<Void> followUser(@PathVariable Integer userId, @AuthenticationPrincipal User user) {
        service.followUser(userId, user.getId());
        
        return ResponseEntity.ok().build();
    }

    // Unfollow a user
    @PatchMapping("/{userId}/unfollow")
    public ResponseEntity<Void> unfollowUser(@PathVariable Integer userId, @AuthenticationPrincipal User user) {
        service.unfollowUser(userId, user.getId());
        return ResponseEntity.ok().build();
    }

    // Get followers of a user
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<UserResponse>> getFollowers(@PathVariable Integer userId) {
        Set<User> followers = service.getFollowers(userId);
        Set<UserResponse> response = followers.stream().map(UserResponse::from).collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

    // Get users the user is following
    @GetMapping("/{userId}/following")
    public ResponseEntity<Set<UserResponse>> getFollowing(@PathVariable Integer userId) {
        Set<User> following = service.getFollowing(userId);
        Set<UserResponse> response = following.stream().map(UserResponse::from).collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }

}
