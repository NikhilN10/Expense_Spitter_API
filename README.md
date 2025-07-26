# 💸 Expense Splitter App

A powerful RESTful API built with **Spring Boot** that allows users to split expenses in groups,
 track who paid how much, and calculate balances efficiently. It supports full **group expense management**, **balance calculation**, and
 is **future-ready** for frontend integration or microservices transition.

---

## 🔧 Tech Stack
--------------------------------------------------
| Layer         | Tech                           |
|---------------|--------------------------------|
| Language      | Java 17                        |
| Framework     | Spring Boot 3                  |
| DB Access     | Spring Data JPA + Hibernate    |
| Database      | MySQL                          |
| Security      | Spring Security + JWT          |
| Docs          | Springdoc OpenAPI (Swagger UI) |
| Dev Tools     | Postman, IntelliJ              |
--------------------------------------------------
---

## 🚀 Features

- ✅ **User & Group Management**
- ✅ **Expense Creation & Splitting**
- ✅ **Balance Calculation (Net Owed / Receive)**
- ✅ **Group-Level Balance Summaries**
- ✅ **Pagination & Search Support**
- ✅ **Global Exception Handling**
- ✅ **Swagger/OpenAPI Docs**
- ✅ **JWT-based Admin Authentication**
- ✅ **Future Support for Frontend (React + TS)**

---

## 🗃️ Project Structure

Expense-Splitter/
├── Controller/
├── Service/
├── DTOs/
├── Model/
├── Repository/
├── Exception/
├── Config/
└── Application.java



---

## 🔐 Auth

JWT is used for securing the endpoints. Once logged in, users receive a token to access protected routes.

Example Header:

Authorization: Bearer <your-token>


---------
📂 Future Scope
Frontend Admin Dashboard (React + TypeScript)

Microservices migration (Eureka, Config Server)

Docker + CI/CD setup

---

## 🛠️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/expense-splitter-api.git
   cd expense-splitter-api

2.Configure MySQL DB

 Create a database named expense_splitter_db

 Update application.properties with your DB credentials

3.Run the application

  ./mvnw spring-boot:run
  
 4. Access Swagger
 http://localhost:8080/swagger-ui/index.html



🧪 API Testing
Use Postman to test:

/api/auth/register

/api/auth/login

/api/expense/create

/api/balance/user



🙌 Contributions
This project is part of my personal backend portfolio to showcase Spring Boot skills. Contributions or feedback are welcome!


-----------------------------------------------------------
🙋‍♂️ Author
Nikhil Belgaonkar
Aspiring Backend Developer 
💼 LinkedIn
📫 Email: nikhilbelgaonkar2000@gamil.com


_______________________________________________________________________________________________________________________________________________________________________________________

⭐️ Show your support
If you found this project useful:

Give it a ⭐️

Fork it 🍴

Share it 💬





