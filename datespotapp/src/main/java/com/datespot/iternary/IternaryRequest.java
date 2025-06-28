package com.datespot.iternary;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IternaryRequest {

    private Integer id;
    private String author;
    private String isbn;
}
