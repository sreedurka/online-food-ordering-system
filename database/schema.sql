CREATE DATABASE IF NOT EXISTS food_ordering_db;
USE food_ordering_db;

CREATE TABLE IF NOT EXISTS menu_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(500),
    category VARCHAR(80) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image_url VARCHAR(500),
    available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(120) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    address VARCHAR(500) NOT NULL,
    payment_method VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    menu_item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_menu
        FOREIGN KEY (menu_item_id) REFERENCES menu_items(id)
);
