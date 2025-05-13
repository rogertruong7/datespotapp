package com.datespot.posts.responses;

import com.datespot.posts.Post;
import org.springframework.stereotype.Component;

@Component
public class PostResponseFactory {

    /**
     * Map a Post entity to the summary DTO used in list endpoints.
     */
    public PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getPostId())
                .location(post.getLocation())
                .rating(post.getRating())
                .reviewText(post.getReviewText())
                .isPublic(post.getIsPublic())
                .authorId(post.getAuthorId())
                .createdAt(post.getCreateDate())
                .build();
    }

    /**
     * Map a newly‐created Post to what you return on POST /api/v1/posts.
     * You can adjust fields here (e.g. only returning id + timestamp).
     */
    public PostCreatedResponse toPostCreatedResponse(Post post) {
        return PostCreatedResponse.builder()
                .id(post.getPostId())
                .createdAt(post.getCreateDate())
                .build();
    }

    /**
     * Map a Post entity to the detailed view with comments.
     * Assumes you have a CommentResponseFactory to handle comments.
     */
    // public PostDetailResponse toPostDetailResponse(Post post) {
    //     var commentsDto = post.getComments().stream()
    //             .map(CommentResponseFactory::toCommentResponse)
    //             .toList();

    //     return PostDetailResponse.builder()
    //             .id(post.getPostId())
    //             .placeId(post.getPlace().getId())
    //             .rating(post.getRating())
    //             .reviewText(post.getReviewText())
    //             .isPublic(post.getIsPublic())
    //             .authorId(post.getAuthor().getId())
    //             .createdAt(post.getCreateDate())
    //             .comments(commentsDto)
    //             .build();
    // }
}
