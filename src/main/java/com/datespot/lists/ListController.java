package com.datespot.lists;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
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
    public ResponseEntity<List<List>> findAllBooks() {
        return ResponseEntity.ok(service.findAll());
    }
}
