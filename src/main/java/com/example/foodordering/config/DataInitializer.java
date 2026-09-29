package com.example.foodordering.config;

import com.example.foodordering.entity.MenuItem;
import com.example.foodordering.repository.MenuItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedMenu(MenuItemRepository repository) {
        return args -> {
            if (repository.count() > 0) return;

            repository.saveAll(List.of(
                    item("Margherita Pizza", "Classic tomato, mozzarella and basil.",
                            "Pizza", "249.00", "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=800"),
                    item("Chicken Burger", "Crispy chicken, lettuce and house sauce.",
                            "Burgers", "189.00", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800"),
                    item("Paneer Wrap", "Spiced paneer, fresh vegetables and mint sauce.",
                            "Wraps", "159.00", "https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=800"),
                    item("Veg Biryani", "Fragrant basmati rice with vegetables and spices.",
                            "Rice", "199.00", "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800"),
                    item("Chicken Biryani", "Aromatic chicken biryani with traditional spices.",
                            "Rice", "259.00", "https://images.unsplash.com/photo-1563379091339-03246963d51a?w=800"),
                    item("Masala Dosa", "Crispy dosa served with potato masala.",
                            "South Indian", "99.00", "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=800"),
                    item("French Fries", "Crispy golden fries with seasoning.",
                            "Sides", "89.00", "https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=800"),
                    item("Chocolate Brownie", "Warm chocolate brownie for dessert.",
                            "Desserts", "119.00", "https://images.unsplash.com/photo-1564355808539-22fda35bed7e?w=800")
            ));
        };
    }

    private MenuItem item(String name, String description, String category,
                          String price, String image) {
        return new MenuItem(name, description, category,
                new BigDecimal(price), image, true);
    }
}
