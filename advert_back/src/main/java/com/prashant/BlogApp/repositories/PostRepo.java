package com.prashant.BlogApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prashant.BlogApp.entities.Category;
import com.prashant.BlogApp.entities.Post;
import com.prashant.BlogApp.entities.User;


public interface PostRepo extends JpaRepository<Post, Integer>{
    
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    // List<Post> findByTitleContaining(String keyword);

    @Query("select p from Post p where LOWER(p.title) like :key")
    List<Post> searchByTitle(@Param("key") String title);
}

