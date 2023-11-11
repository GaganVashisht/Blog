package com.project.blog.services.impl;

import com.project.blog.entites.Category;
import com.project.blog.entites.Post;
import com.project.blog.entites.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.repositories.CategoryRespository;
import com.project.blog.repositories.PostRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRespository categoryRespository;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        User user =userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));
        Category category=categoryRespository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));


        Post post=modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post savedPost=postRepository.save(post);

        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        Post updatedPost=postRepository.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        postRepository.delete(post);

    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
//        System.out.println(pageNumber+" "+pageSize);
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }else{
            sort=Sort.by(sortBy).descending();
        }
        Pageable p= PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost= postRepository.findAll(p);
        List<Post> postList= pagePost.getContent();
//        System.out.println(pagePost);
         List<PostDto> pageDtoList=postList.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
         PostResponse res=new PostResponse();
         res.setContent(pageDtoList);
         res.setPageNumber(pagePost.getNumber());
         res.setPageSize(pagePost.getSize());
         res.setTotalElements(pagePost.getTotalElements());

         res.setTotalPages(pagePost.getTotalPages());
         res.setLastPage(pagePost.isLast());

        return res;
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {
        Category category= categoryRespository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        Pageable p=PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost= postRepository.findByCategory(category,p);
        List<Post> postList= pagePost.getContent();
//        System.out.println(pagePost);
        List<PostDto> pageDtoList=postList.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse res=new PostResponse();
        res.setContent(pageDtoList);
        res.setPageNumber(pagePost.getNumber());
        res.setPageSize(pagePost.getSize());
        res.setTotalElements(pagePost.getTotalElements());

        res.setTotalPages(pagePost.getTotalPages());
        res.setLastPage(pagePost.isLast());

        return res;
    }

    @Override
    public PostResponse getPostsByUser(Integer userId,Integer pageNumber,Integer pageSize) {
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User id",userId));
        Pageable p=PageRequest.of(pageNumber,pageSize);
        Page<Post> pagePost= postRepository.findByUser(user,p);
        List<Post> postList= pagePost.getContent();
//        System.out.println(pagePost);
        List<PostDto> pageDtoList=postList.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse res=new PostResponse();
        res.setContent(pageDtoList);
        res.setPageNumber(pagePost.getNumber());
        res.setPageSize(pagePost.getSize());
        res.setTotalElements(pagePost.getTotalElements());

        res.setTotalPages(pagePost.getTotalPages());
        res.setLastPage(pagePost.isLast());

        return res;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> postList=postRepository.searchByTitle("%"+keyword+"%");

        return postList.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

    }


}
