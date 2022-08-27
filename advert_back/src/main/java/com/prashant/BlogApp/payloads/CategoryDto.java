package com.prashant.BlogApp.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
  
    private Integer id;

    private String categoryTitle;

    private String categoryDescription;
}
