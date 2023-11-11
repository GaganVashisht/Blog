package com.project.blog.controllers;


import com.project.blog.config.AppConstants;
import com.project.blog.payloads.ApiResponse;
import com.project.blog.payloads.PostDto;
import com.project.blog.payloads.PostResponse;
import com.project.blog.services.FileService;
import com.project.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId, @PathVariable Integer categoryId){
       PostDto savedPost= postService.createPost(postDto,userId,categoryId);

       return new ResponseEntity<PostDto>(savedPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostByUser(@PathVariable Integer userId,@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                      @RequestParam(value = "pageSize",defaultValue=AppConstants.PAGE_SIZE,required = false) Integer pageSize){
        PostResponse postResponse= postService.getPostsByUser(userId, pageNumber, pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostByCategory(@PathVariable Integer categoryId,@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize",defaultValue=AppConstants.PAGE_SIZE,required = false) Integer pageSize){
        PostResponse postResponse= postService.getPostByCategory(categoryId,pageNumber,pageSize);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(value = "pageSize",defaultValue=AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                   @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                   @RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
       PostResponse postResponse=postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto=postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse(postId+ ": Post Deleted Successfully",true),HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer postId,@RequestBody PostDto postDto){
        PostDto updatedPostDto=postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPostDto,HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<?> searchPost(@PathVariable("keywords") String keywords){
       List<PostDto> postDtoList= postService.searchPost(keywords);
       if(postDtoList.isEmpty()){
           return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<List<PostDto>>(postDtoList,HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> updatePostImage(@RequestParam("image")MultipartFile image,@PathVariable Integer postId) throws IOException {

        String fileName=fileService.uploadImage(path,image);
        PostDto postDto=postService.getPostById(postId);
        postDto.setImageName(fileName);
        PostDto updatePost=postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);

    }

    @GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws  IOException{
        InputStream resource =this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
