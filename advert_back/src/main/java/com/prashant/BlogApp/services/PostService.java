package com.prashant.BlogApp.services;


import java.util.List;

// import com.prashant.BlogApp.entities.Post;
import com.prashant.BlogApp.payloads.PostDto;
import com.prashant.BlogApp.payloads.PostResponse;

public interface PostService {
    
    PostDto createPost(PostDto PostDto, Integer userId, Integer categoryId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostsByCategory(Integer categoryId);

    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> searchPosts(String keyword);
}
