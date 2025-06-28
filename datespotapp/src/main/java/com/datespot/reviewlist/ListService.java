package com.datespot.reviewlist;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListRepository repository;

    public void save(ListRequest request) {
        var book = ReviewList.builder()
                .id(request.getId())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .build();
        repository.save(book);
    }

    public List<ReviewList> findAll() {
        return repository.findAll();
    }
}
