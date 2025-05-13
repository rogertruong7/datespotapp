package com.datespot.iternary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IternaryService {

    private final IternaryRepository repository;

    public void save(IternaryRequest request) {
        var book = Iternary.builder()
                .id(request.getId())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .build();
        repository.save(book);
    }

    public List<Iternary> findAll() {
        return repository.findAll();
    }
}
