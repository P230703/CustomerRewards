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

-GET /api/rewards/{customerId}?start=YYYY-MM-DD&end=YYYY-MM-DD
-Path Variable: customerId → customer ID
-Query Params (optional):
start → Start date in UTC (default: 3 months ago)
end → End date in UTC (default: today)

example:

{
  "customerId": 1,
  "totalPoints": 115,
  "pointsPerMonth": {
    "2025-01": 90,
    "2025-02": 25
  },
  "purchases": [
    {
      "id": 1,
      "customerId": 1,
      "amount": 120.5,
      "date": "2025-01-10"
    },
    {
      "id": 2,
      "customerId": 1,
      "amount": 75.0,
      "date": "2025-02-05"
    }
  ]
}

Test Evidence:
-Build success screenshot: docs/BuildSuccess.png

Set up and Build Instructions
1. Clone the Repository
   git clone https://github.com/P230703/CustomerRewards.git
   cd CustomerRewards
2. Configure the Database
   Update your src/main/resources/application.properties file with your MySQL credentials:
   spring.datasource.url=jdbc:mysql://localhost:3306/rewardsdb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
3. Build the Application
   Run the following commands from the project root:
   mvn clean install
   If the build is successful, you’ll see "BUILD SUCCESS" in the terminal.

Run Application
To start the Spring Boot application, use:
mvn spring-boot:run
The app will start at:
http://localhost:8080
