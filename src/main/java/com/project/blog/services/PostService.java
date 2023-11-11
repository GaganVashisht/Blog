package com.project.blog.services;

import com.project.blog.entites.Post;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

    PostDto updatePost(PostDto postDto,Integer postId);

    void deletePost(Integer postId);
    PostDto getPostById(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    PostResponse getPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize);
    PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize);

    List<PostDto> searchPost(String keyword);

}
