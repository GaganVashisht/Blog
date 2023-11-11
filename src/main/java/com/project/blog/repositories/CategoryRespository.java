package com.project.blog.repositories;

import com.project.blog.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRespository extends JpaRepository<Category,Integer> {

}
