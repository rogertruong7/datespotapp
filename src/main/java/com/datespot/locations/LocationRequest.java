package com.datespot.locations;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationRequest {

    private Integer id;
    private String author;
    private String isbn;
}
