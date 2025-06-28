package com.datespot.reviewlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<ReviewList, Integer> {
}
