package com.datespot.reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Page<Post> findAllByAuthorId(Integer authorId, Pageable pageable);
    Page<Post> findByIsPublicTrue(Pageable pageable);
}
