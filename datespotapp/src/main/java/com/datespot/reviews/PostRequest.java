package com.datespot.reviews;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
/**
 * Data Transfer Object for creating or updating a Post.
 *
 * Encapsulates only the fields that clients are allowed to send in
 * the request body, decoupling the API contract from the internal
 * JPA entity.
 *
 */
public class PostRequest {
    private String reviewTitle;
    private String reviewText;
    private String location;
    private Boolean isPublic;
    private Integer rating;
}
