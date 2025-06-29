package com.datespot.reviews;

import java.time.LocalDateTime;

public record PostResponse(
        Integer id,
        String location,
        Integer rating,
        String reviewTitle,
        String reviewText,
        Boolean isPublic,
        Integer authorId,
        LocalDateTime createDate,
        LocalDateTime lastModified) {
    public static PostResponse DefaultResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                post.getLocation(),
                post.getRating(),
                post.getReviewTitle(),
                post.getReviewText(),
                post.getIsPublic(),
                post.getAuthorId(),
                post.getCreateDate(),
                post.getLastModified());
    }

    public static PostResponse CreatedResponse(Post post) {
        return new PostResponse(
                post.getPostId(),
                null,
                null,
                null,
                null,
                null,
                post.getAuthorId(),
                post.getCreateDate(),
                null);
    }
}
