package com.datespot.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    // /**
    // * List a user’s posts.
    // * GET /api/v1/users/{userId}/posts
    // */
    // @GetMapping(path = "/users/{userId}")
    // public ResponseEntity<List<Post>> listUserPosts(@PathVariable Long userId) {
    // List<Post> posts = postService.findByUser(userId);
    // return ResponseEntity.ok(posts);
    // }
}
