package com.datespot.posts.responses;

import com.datespot.posts.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Integer id;
    private String location;
    private Rating rating;
    private String reviewText;
    private Boolean isPublic;
    private Integer authorId;
    private LocalDateTime createdAt;
}
