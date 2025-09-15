This is a Spring Boot application that calculates reward points for customers based on their purchases.It uses a clean Controller → Service → Repository architecture with MySQL as the database.
The system awards points as follows:

-2 points for every dollar spent above $100

-1 point for every dollar spent between $50 and $100

-0 points for purchases below $50

Tech Stack :

-Java 17
-Spring Boot 3+
-Spring Data Jpa
-Lombok
-JUnit 5
-Mockito
-MySQL

API Endpoints:

1.Create Purchase
POST '/api/purchases'
-Request
{
  "customerId": 1,
  "amount": 120.50,
  "date": "2025-01-10"
}
-Response
{
  "id": 1,
  "customerId": 1,
  "amount": 120.5,
  "date": "2025-01-10"
}

2.Get Rewards
GET '/api/rewards/{customerId}?start=2025-01-01&end=2025-03-31'
{
  "customerId": 1,
  "totalPoints": 115,
  "pointsPerMonth": {
    "2025-01": 90,
    "2025-02": 25
  },
  "purchases": [...]
}


