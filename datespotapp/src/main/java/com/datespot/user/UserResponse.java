package com.datespot.user;

public record UserResponse(Integer id, String username, String firstname, String lastname) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getFirstname(), user.getLastname());
    }
}
