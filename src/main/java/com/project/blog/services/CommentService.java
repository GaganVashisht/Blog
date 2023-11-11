package com.project.blog.services;

import com.project.blog.entites.Comment;
import com.project.blog.payloads.CommentDto;


public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);

    void deleteComment(Integer commentId);
}
