package com.example.foodordering.repository;

import com.example.foodordering.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByAvailableTrueOrderByCategoryAscNameAsc();
    List<MenuItem> findByAvailableTrueAndNameContainingIgnoreCaseOrderByNameAsc(String keyword);
    List<MenuItem> findByAvailableTrueAndCategoryIgnoreCaseOrderByNameAsc(String category);
}
