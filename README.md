
```markdown
# Northwind E-commerce API (Spring Boot)

An E-commerce Backend RESTful API modernizing the classic Northwind database. Built with **Java 21**, **Spring Boot 3**, and **PostgreSQL**, focusing on scalability, security, and clean architecture.

## 🚀 Technologies

- **Core:** Java 21, Spring Boot 3.x
- **Database:** PostgreSQL, Hibernate/JPA
- **Security:** Spring Security, JWT (Access & Refresh Tokens), BCrypt
- **Build Tool:** Maven
- **Deployment:** Docker Compose
- **API Documentation:** OpenAPI (Swagger UI)
- **Utilities:** ModelMapper, Lombok, Jakarta Validation

## 🌟 Key Features

### 1. Authentication & Authorization
- **Secure Login/Register:** User registration and authentication using JWT.
- **Refresh Token Rotation:** Implemented secure refresh token mechanism for long-lived sessions.
- **RBAC (Role-Based Access Control):** - `ROLE_ADMIN`: Full system access.
  - `ROLE_MANAGER`: Product, Category, and Supplier management.
  - `ROLE_USER`: Order placement and viewing history.

### 2. Product & Category Management
- Full CRUD operations for Products and Categories.
- Product search functionality.
- Image upload support for Categories.

### 3. Order Processing
- **Transactional Ordering:** Ensures data consistency when creating Orders and Order Details simultaneously.
- Validation for stock availability and pricing.
- Order history tracking for customers.

### 4. Supply Chain
- Management of Suppliers and Shippers.
- Restricted access for Managers and Admins.

## 🛠️ Getting Started

### Prerequisites
- Java 21
- Docker & Docker Compose
- Maven

### Installation

1. **Clone the repository**
   ```bash
   git clone [https://github.com/your-username/E_Commerce.git](https://github.com/your-username/E_Commerce.git)
   cd E_Commerce

```

2. **Database Setup**
Start the PostgreSQL container using Docker Compose:
```bash
docker-compose up -d

```


*Note: This starts PostgreSQL on port 5432 with database `ecommerce`.*
3. **Configuration**
Check `src/main/resources/application.properties` to ensure database credentials match your local setup or Docker container.
4. **Run the Application**
```bash
./mvnw spring-boot:run

```


The application will start on **port 8888**.

## 📚 API Documentation

Once the application is running, you can access the interactive API documentation (Swagger UI) at:

```
http://localhost:8888/swagger-ui/index.html

```

## 🗄️ Database Schema

The project uses a modernized version of the **Northwind Database** schema, including tables for:

* `users`, `roles`, `user_roles` (Auth)
* `products`, `categories`, `suppliers`
* `orders`, `order_details`, `shippers`
* `employees`, `employee_territories`

## 🧪 Testing

Run unit and integration tests using Maven:

```bash
./mvnw test

```


