# 🔐 Spring Boot 3 JWT Authentication

This project demonstrates how to implement **JWT (JSON Web Token)** authentication using **Spring Boot 3** and **JDK 21**. The application includes endpoints for login, signup, and token validation, using JWT for secure user authentication.

## 🚀 Features

- **JWT Authentication**: Secure authentication using JWT for stateless token-based access.
- **Spring Security**: Leverages Spring Security to secure API endpoints.
- **User Signup & Login**: Basic endpoints for user registration and login.
- **Token Expiration**: Access tokens are time-limited, ensuring security.

## 🛠️ Technology Stack

- **Java**: JDK 21
- **Spring Boot**: Version 3.x
- **Spring Security**: Integrated security framework.
- **JWT**: Token-based authentication.
- **Maven**: Dependency management.

## 📋 Prerequisites

- **JDK 21** installed
- **Maven** installed
- **Git** for version control (optional)

## ⚙️ Setup & Run

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/your-repo.git
    cd your-repo
    ```

2. Build the project:

    ```bash
    mvn clean install
    ```

3. Run the application:

    ```bash
    mvn spring-boot:run
    ```

4. Access the application at `http://localhost:8080`.

## 📝 Endpoints

### 1. User Signup

```http
POST /signup

POST /login