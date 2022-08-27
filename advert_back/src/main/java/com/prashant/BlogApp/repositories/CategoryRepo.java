package com.prashant.BlogApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashant.BlogApp.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    
}
