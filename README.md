# Online Food Ordering System

A complete full-stack food ordering application built with:

- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Backend:** Java 17 + Spring Boot 3
- **Database:** MySQL 8+
- **Build:** Maven
- **REST API:** Spring Web
- **Persistence:** Spring Data JPA / Hibernate

## Design Patterns Implemented

1. **Repository Pattern** – database access is isolated in repository interfaces.
2. **Service Layer Pattern** – business logic is separated from controllers.
3. **Builder Pattern** – `OrderBuilder` creates an `Order` object cleanly.
4. **Strategy Pattern** – payment methods are interchangeable:
   - Cash on Delivery
   - UPI
   - Card
5. **Factory Pattern** – `PaymentStrategyFactory` selects the correct payment strategy.
6. **Observer Pattern** – order status notifications are published to registered observers.
7. **DTO Pattern** – request/response objects prevent exposing entities directly.

## Features

- Browse food menu
- Search and filter menu
- Add/remove items from cart
- Quantity controls
- Checkout form
- Customer order creation
- Payment strategy selection
- Order tracking/status
- MySQL persistence
- Sample menu automatically seeded on first startup
- Responsive UI

---

# 1. Prerequisites

Install:

- JDK 17 or newer
- Maven 3.9+
- MySQL 8+
- IntelliJ IDEA / Eclipse / VS Code (optional)

Verify:

```bash
java -version
mvn -version
mysql --version
```

---

# 2. Create the MySQL Database

Open MySQL:

```sql
CREATE DATABASE food_ordering_db;
```

The application will create/update its tables automatically using Hibernate.

You can also run:

```text
database/schema.sql
```

The schema script creates the database and tables if you prefer manual setup.

---

# 3. Configure MySQL

Open:

```text
src/main/resources/application.properties
```

Change these values:

```properties
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

Example:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

The default database URL is:

```text
jdbc:mysql://localhost:3306/food_ordering_db
```

---

# 4. Run the Application

From the project directory:

```bash
mvn clean spring-boot:run
```

Or build a JAR:

```bash
mvn clean package
java -jar target/online-food-ordering-system-1.0.0.jar
```

Open:

```text
http://localhost:8080
```

The frontend is served directly by Spring Boot from:

```text
src/main/resources/static
```

---

# 5. REST API

## Menu

### Get all menu items

```http
GET /api/menu
```

### Search menu

```http
GET /api/menu/search?keyword=pizza
```

### Filter by category

```http
GET /api/menu/category/Pizza
```

## Orders

### Create order

```http
POST /api/orders
Content-Type: application/json
```

Example:

```json
{
  "customerName": "Sree",
  "phone": "9876543210",
  "address": "Madurai, Tamil Nadu",
  "paymentMethod": "UPI",
  "items": [
    {
      "menuItemId": 1,
      "quantity": 2
    },
    {
      "menuItemId": 4,
      "quantity": 1
    }
  ]
}
```

### Get order

```http
GET /api/orders/{id}
```

### Update order status

```http
PUT /api/orders/{id}/status
Content-Type: application/json
```

Example:

```json
{
  "status": "CONFIRMED"
}
```

---

# 6. Design Pattern Explanation

## Builder

`OrderBuilder` constructs an order step-by-step:

```text
customer details
      ↓
order items
      ↓
total amount
      ↓
payment method
      ↓
Order
```

This avoids a large constructor with many parameters.

## Strategy

Payment behavior changes without changing the order service:

```text
PaymentStrategy
      |
      +-- CashPaymentStrategy
      +-- UpiPaymentStrategy
      +-- CardPaymentStrategy
```

Each strategy implements:

```java
PaymentResult pay(Order order);
```

## Factory

The factory selects the strategy:

```text
"COD"  → CashPaymentStrategy
"UPI"  → UpiPaymentStrategy
"CARD" → CardPaymentStrategy
```

## Observer

When an order status changes:

```text
OrderService
    ↓
OrderStatusPublisher
    ↓
Email/SMS-style notification observer
```

The included observer logs a notification. It is intentionally provider-independent so an actual email/SMS provider can be added later.

## Repository

Spring Data repositories handle persistence:

```text
Controller
   ↓
Service
   ↓
Repository
   ↓
MySQL
```

---

# 7. Project Structure

```text
online-food-ordering-system/
├── database/
│   └── schema.sql
├── src/
│   ├── main/
│   │   ├── java/com/example/foodordering/
│   │   │   ├── FoodOrderingApplication.java
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── observer/
│   │   │   ├── payment/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── index.html
│   │           ├── css/style.css
│   │           └── js/app.js
├── pom.xml
└── README.md
```

---

# 8. Important Notes

This project is designed as a **college/placement project** demonstrating full-stack development and design patterns.

The payment implementation is a simulation. It does NOT connect to a real payment gateway.

For production use, add:

- Authentication / JWT
- Password hashing
- Admin dashboard
- Real payment gateway
- Real email/SMS provider
- Input validation
- Rate limiting
- HTTPS
- Proper CORS/security configuration
- Docker
- Production database migrations with Flyway/Liquibase

---

# 9. Suggested Viva Questions

### Why use Strategy Pattern?

Because payment behavior can change independently. Adding a new payment method does not require rewriting `OrderService`.

### Why use Factory Pattern?

The factory centralizes creation/selection of payment strategies.

### Why use Builder?

An order has several fields and a variable number of items. Builder makes object construction readable.

### Why use Repository?

It separates database operations from business logic.

### Why use DTOs?

DTOs control the API contract and prevent exposing internal entity structure directly.

### Why Spring Boot?

It provides dependency injection, REST support, JPA integration, validation and embedded server support with less configuration.

### Why MySQL?

The system has structured relational data such as customers, menu items, orders and order items, making an SQL database suitable.

---

# 10. Quick Start

```bash
# 1. Create database
mysql -u root -p < database/schema.sql

# 2. Update password
# Edit src/main/resources/application.properties

# 3. Run
mvn spring-boot:run

# 4. Open
http://localhost:8080
```
