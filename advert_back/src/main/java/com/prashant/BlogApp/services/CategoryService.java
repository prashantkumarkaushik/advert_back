package com.prashant.BlogApp.services;

import java.util.List;

import com.prashant.BlogApp.payloads.CategoryDto;

public interface CategoryService {

    //Create
    CategoryDto createCategory(CategoryDto categoryDto);

    //Update
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    
    //Delete
    void deleteCategory(Integer categoryId);

    //Get by Id
    CategoryDto getCategory(Integer categoryId);

    //Get all categories
    List<CategoryDto> getCategories();


}
