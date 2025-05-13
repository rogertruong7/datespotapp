package com.datespot.lists;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ListRequest {

    private Integer id;
    private String author;
    private String isbn;
}
