package com.datespot.posts.responses;

import com.datespot.posts.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailResponse {
    private Integer id;
    private String location;
    private Rating rating;
    private String reviewText;
    private Boolean isPublic;
    private Integer authorId;
    private LocalDateTime createdAt;
    // private List<CommentResponse> comments;
}
