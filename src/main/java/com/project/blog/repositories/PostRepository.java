package com.project.blog.repositories;

import com.project.blog.entites.Category;
import com.project.blog.entites.Post;
import com.project.blog.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findByUser(User user,Pageable p);

    Page<Post> findByCategory(Category category, Pageable p);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);

}
