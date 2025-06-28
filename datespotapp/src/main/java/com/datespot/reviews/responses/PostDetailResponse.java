package com.datespot.reviews.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Integer id;
    private String location;
    private Integer rating;
    private String reviewText;
    private Boolean isPublic;
    private Integer authorId;
    private LocalDateTime createdAt;
    // private List<CommentResponse> comments;
}
