# Customer Rewards Program

## Overview

A **Spring Boot** application that calculates **reward points** for customers based on their purchases.
Follows a **Controller → Service → Repository** architecture and uses **MySQL** as the database.

### Reward Rules

* 2 points for every dollar spent above $100
* 1 point for every dollar spent between $50–$100
* 0 points for purchases below $50

---

## Tech Stack

* Java 17
* Spring Boot 3+
* Spring Data JPA
* Lombok
* JUnit 5
* Mockito
* MySQL

---

## Build & Run Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/P230703/CustomerRewards.git
cd CustomerRewards
```

### 2. Configure the Database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/rewardsdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

App will run at: **[http://localhost:8080](http://localhost:8080)**

---

## API Endpoint

**GET** `/api/rewards/{customerId}?start=YYYY-MM-DD&end=YYYY-MM-DD`

* **Path Variable:** `customerId` → Customer ID
* **Query Params (optional):**

  * `start` → Start date (default: 3 months ago)
  * `end` → End date (default: today)

### Example Response

```json
{
  "customerId": 1,
  "totalPoints": 115,
  "pointsPerMonth": {
    "2025-01": 90,
    "2025-02": 25
  }
}
```

---

## Test Evidence

* **Build Success Screenshot:** `Docs/BuildSuccess.png`

To run tests:

```bash
mvn test
```


**Database**

CREATE DATABASE IF NOT EXISTS rewards_db;
USE rewards_db;

DROP TABLE IF EXISTS purchases;

CREATE TABLE purchases (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    date DATE NOT NULL
);

INSERT INTO purchases (customer_id, amount, date) VALUES (1, 120.00, '2025-01-15');
INSERT INTO purchases (customer_id, amount, date) VALUES (1, 75.00, '2025-01-20');
INSERT INTO purchases (customer_id, amount, date) VALUES (2, 200.00, '2025-02-10');
INSERT INTO purchases (customer_id, amount, date) VALUES (3, 45.00, '2025-03-05');

select * from purchases;
