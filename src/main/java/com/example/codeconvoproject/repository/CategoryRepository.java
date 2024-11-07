package com.example.codeconvoproject.repository;

import com.example.codeconvoproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String categoryName);
}
