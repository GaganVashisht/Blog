package com.project.blog.services.impl;


import com.project.blog.entites.Comment;
import com.project.blog.entites.Post;
import com.project.blog.entites.User;
import com.project.blog.exceptions.ResourceNotFoundException;
import com.project.blog.payloads.CommentDto;
import com.project.blog.repositories.CommentRespository;
import com.project.blog.repositories.PostRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRespository commentRespository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        User user=userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id",userId));
        Comment comment=modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment saveComment=commentRespository.save(comment);
        return modelMapper.map(saveComment,CommentDto.class);
    }



    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=commentRespository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","Comment Id",commentId));
        commentRespository.delete(comment);
    }
}
