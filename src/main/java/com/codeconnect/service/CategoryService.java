package com.codeconnect.service;

import com.codeconnect.entity.Category;
import com.codeconnect.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category getCategory(String categoryName) {
        List<Category> categoryPs = categoryRepository.findByName(categoryName);

        if (categoryPs.isEmpty()) {
            throw new EntityNotFoundException("해당 이름의 카테고리를 찾을 수 없습니다: " + categoryName);
        }

        // category name은 unique 속성이므로, categoryPs.isEmpty()가 true일 때, get(0)만 존재
        return categoryPs.get(0);
    }
}
