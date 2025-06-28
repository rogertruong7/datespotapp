package com.datespot.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeProfileRequest {

    private Boolean isPublic;
    private String profilePicture;
    private String biography;
}
