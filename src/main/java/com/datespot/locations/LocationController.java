package com.datespot.locations;

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
public class LocationController {

    private final LocationService service;

    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestBody LocationRequest request) {
        service.save(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/find")
    public ResponseEntity<List<Location>> findAllBooks() {
        return ResponseEntity.ok(service.findAll());
    }
}
