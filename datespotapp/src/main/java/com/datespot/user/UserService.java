package com.datespot.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.datespot.reviews.PostService;

import jakarta.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
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
        repository.save(user);
    }

    // Transactional means either all changes go through or none, compulsory for jakarta persistance
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
            repository.save(connectedUser);
        }
    }

}
