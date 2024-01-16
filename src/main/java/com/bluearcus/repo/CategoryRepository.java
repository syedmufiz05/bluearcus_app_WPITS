package com.bluearcus.repo;

import com.bluearcus.dto.CategoryDto;
import com.bluearcus.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select new com.bluearcus.dto.CategoryDto(ctg.id,ctg.name)from Category ctg")
    List<CategoryDto> fetchAllCategory();

    Optional<Category> findByName(String name);
}
