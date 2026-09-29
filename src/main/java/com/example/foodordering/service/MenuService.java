package com.example.foodordering.service;

import com.example.foodordering.entity.MenuItem;
import com.example.foodordering.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuItemRepository repository;

    public MenuService(MenuItemRepository repository) {
        this.repository = repository;
    }

    public List<MenuItem> getAll() {
        return repository.findByAvailableTrueOrderByCategoryAscNameAsc();
    }

    public List<MenuItem> search(String keyword) {
        if (keyword == null || keyword.isBlank()) return getAll();
        return repository.findByAvailableTrueAndNameContainingIgnoreCaseOrderByNameAsc(keyword.trim());
    }

    public List<MenuItem> byCategory(String category) {
        return repository.findByAvailableTrueAndCategoryIgnoreCaseOrderByNameAsc(category);
    }
}
