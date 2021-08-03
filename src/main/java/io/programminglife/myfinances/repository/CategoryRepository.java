package io.programminglife.myfinances.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.programminglife.myfinances.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category WHERE label = :label", nativeQuery = true)
    Optional<Category> findCategoryByLabel(@Param("label") String label);
    
}