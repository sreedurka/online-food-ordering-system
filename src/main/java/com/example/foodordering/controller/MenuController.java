package com.example.foodordering.controller;

import com.example.foodordering.entity.MenuItem;
import com.example.foodordering.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<MenuItem> getMenu() {
        return menuService.getAll();
    }

    @GetMapping("/search")
    public List<MenuItem> search(@RequestParam String keyword) {
        return menuService.search(keyword);
    }

    @GetMapping("/category/{category}")
    public List<MenuItem> byCategory(@PathVariable String category) {
        return menuService.byCategory(category);
    }
}
