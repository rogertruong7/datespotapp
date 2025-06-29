package com.datespot.reviews;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser_Id(Integer authorId);

    Page<Post> findByIsPublicTrue(Pageable pageable);

    @Modifying
    @Query("UPDATE Post p SET p.isPublic = :isPublic WHERE p.user.id = :authorId")
    void updatePostVisibilityByAuthorId(@Param("isPublic") Boolean isPublic, @Param("authorId") Integer authorId);
}
