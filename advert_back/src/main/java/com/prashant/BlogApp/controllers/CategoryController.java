package com.prashant.BlogApp.controllers;

import java.util.List;
// import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prashant.BlogApp.payloads.ApiResponse;
import com.prashant.BlogApp.payloads.CategoryDto;
import com.prashant.BlogApp.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //POST ->  create user
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createUser(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createCategoryDto=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
    }

    // GET -> find all users
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllUsers() {
        return ResponseEntity.ok(this.categoryService.getCategories());
    }

    // DELETE -> delete user
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("categoryId") Integer cid) {
        this.categoryService.deleteCategory(cid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully", true), HttpStatus.OK);
    }
    // PUT -> update user

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer cid) {
        CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, cid);
        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getUserById(@PathVariable("categoryId") Integer cid) {
        CategoryDto category = this.categoryService.getCategory(cid);
        return ResponseEntity.ok(category);
    } 
}
