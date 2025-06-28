package com.datespot.reviewlist;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lists")
@RequiredArgsConstructor
public class ListController {

    private final ListService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestBody ListRequest request) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<ReviewList>> findAllBooks() {
        return ResponseEntity.ok(service.findAll());
    }
}
