package com.prashant.BlogApp.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.prashant.BlogApp.entities.Category;
import com.prashant.BlogApp.entities.Post;
import com.prashant.BlogApp.entities.User;
import com.prashant.BlogApp.exceptions.ResourceNotFoundException;
import com.prashant.BlogApp.payloads.PostDto;
import com.prashant.BlogApp.payloads.PostResponse;
import com.prashant.BlogApp.repositories.CategoryRepo;
import com.prashant.BlogApp.repositories.PostRepo;
import com.prashant.BlogApp.repositories.UserRepo;
import com.prashant.BlogApp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    //Create Post
    @Override
    public PostDto createPost(PostDto PostDto, Integer userId, Integer categoryId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userId", " id ", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("categoryId", " id ", categoryId));
        
        Post post = this.modelMapper.map(PostDto, Post.class);
        // post.setImageName("defaultImage.png");
        post.setAddedDate(new Date());
        
        post.setUser(user);
        post.setCategory(category);
        
        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
    }

    //update Post
    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("postId", " Id ", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepo.save(post);
        PostDto updatedPostDto = modelMapper.map(updatedPost, PostDto.class);
        return updatedPostDto;
    }

    // delete Post
    @Override
    public void deletePost(Integer postId) {
       Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", " Id ", postId));
       postRepo.delete(post); 
    }

    // Get all posts
    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = null;
        if(sortDir.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent();

        List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    // Get posts By id
    @Override
    public PostDto getPostById(Integer postId) {
        Post post =this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("postId", " Id ", postId));
        PostDto postDto = this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    // get post by category
    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("categoryId", " Id ", categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        List<PostDto> postDtos=posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    //get all posts from a particular user
    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("userId", " Id ", userId));
        List<Post> posts = this.postRepo.findByUser(user);
        List<PostDto> postDtos=posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.searchByTitle("%"+keyword +"%");
        List<PostDto> postDtos = posts.stream().map(
            (post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
