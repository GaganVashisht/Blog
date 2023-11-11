package com.project.blog.repositories;

import com.project.blog.entites.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<Comment,Integer> {


}
