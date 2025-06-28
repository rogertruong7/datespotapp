package com.datespot.reviews;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
/**
 * Data Transfer Object for updating a Post.
 *
 * Encapsulates only the fields that clients are allowed to send in
 * the request body, decoupling the API contract from the internal
 * JPA entity.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditPostRequest {
    private String reviewTitle;
    private String reviewText;
    private Boolean isPublic;
    private String location;
}