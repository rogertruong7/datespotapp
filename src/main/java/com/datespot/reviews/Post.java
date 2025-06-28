package com.datespot.reviews;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Represents a user-generated post containing a review for a specific location.
 * Includes metadata such as author, visibility, timestamps, and rating details.
 *
 * This entity is mapped to a relational database table via JPA annotations.
 * Auditing fields are automatically populated by Spring Data JPA's auditing
 * features.
 *
 * Each post has a one-to-one relationship with a {@link Rating} entity,
 * which is automatically cascaded and removed if the post is deleted.
 *
 * @author roger
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	@Column(nullable = false)
	private Integer authorId;

	@Column(nullable = false, length = 2048)
	private String reviewText;

	@Column(nullable = false)
	private String location;

	@Column(nullable = false)
	private Boolean isPublic;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "rating_id", referencedColumnName = "ratingId")
	private Rating rating;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;

	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModified;
}
