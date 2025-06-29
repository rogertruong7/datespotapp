package com.datespot.user;

import lombok.RequiredArgsConstructor;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.datespot.reviews.PostService;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PostService postService;

    public void changePassword(ChangePasswordRequest request, User user) {

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }

    // Transactional means either all changes go through or none, compulsory for
    // jakarta persistance
    @Transactional
    public void changeProfile(ChangeProfileRequest request, User connectedUser) {
        boolean changed = false;

        // Also sets all posts of private
        if (request.getIsPublic() != null) {
            connectedUser.setIsPublic(request.getIsPublic());
            changed = true;

            postService.updatePostVisibilityByAuthorId(request.getIsPublic(), connectedUser.getId());
        }

        if (request.getProfilePicture() != null) {
            connectedUser.setProfilePicture(request.getProfilePicture());
            changed = true;
        }

        if (request.getBiography() != null) {
            connectedUser.setBiography(request.getBiography());
            changed = true;
        }

        if (changed) {
            userRepository.save(connectedUser);
        }
    }

    @Transactional
    public void followUser(Integer userIdToFollow, Integer userId) {
        if (userIdToFollow == userId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot follow yourself");
        }
        User currentUser = userRepository.findById(
                userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current user not found"));

        User userToFollow = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        currentUser.addFollowing(userToFollow);
        userToFollow.addFollower(currentUser);
        userRepository.save(currentUser);
        userRepository.save(userToFollow);
    }

    @Transactional
    public void unfollowUser(Integer userIdToUnfollow, Integer userId) {
        if (userIdToUnfollow == userId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot unfollow yourself");
        }
        User currentUser = userRepository.findById(
                userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Current user not found"));
        User userToUnfollow = userRepository.findById(userIdToUnfollow)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        currentUser.removeFollowing(userToUnfollow);
        userToUnfollow.removeFollower(currentUser);
        userRepository.save(currentUser);
        userRepository.save(userToUnfollow);
    }

    public Set<User> getFollowers(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getFollowers();
    }

    public Set<User> getFollowing(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user.getFollowing();
    }

}
