JobPortal Microservices 🚀
A robust, production-grade job portal backend built using Spring Boot Microservices Architecture. 

This project features centralized configuration, service discovery, an API gateway, and JWT-based security with MongoDB Atlas integration.

🏗️ Architecture Overview

The system is designed with a highly scalable infrastructure layer and independent business services:

Discovery Server (Netflix Eureka): Acts as the service registry for all microservices. 

API Gateway (Spring Cloud Gateway): The single entry point for all requests, providing routing and security filtering.

Config Server (Spring Cloud Config): Centralizes application configurations (Native file system storage).

Identity Service: Handles user registration, login, and JWT generation/validation using MongoDB Atlas.

🛠️ Tech StackBackend: Java 23, Spring Boot 3.x, Spring Cloud

Security: Spring Security, JSON Web Tokens (JWT), BCrypt Password Hashing

Database: MongoDB Atlas (Cloud)Documentation: Swagger / OpenAPI 3

Environment Management: Dotenv for sensitive credentials🚦 Port Mapping ServicePort Description Discovery Server8761
Eureka Dashboard Config Server2222 Centralized Configurations API Gateway2020 Primary Entry Point Identity Service 2021 Auth & User Management

🚀 Getting Started
1. PrerequisitesJDK 23+Maven 3.xA MongoDB Atlas Cluster
2. Setup Environment VariablesCreate a .env file in the root directory:
Code snippet ```MONGO_URI=your_mongodb_atlas_uri```
JWT_SECRET=your_base64_encoded_secret_key
3. Build & RunPowerShell# Build all modules
   ./mvnw clean install

# Start in order:
# 1. Discovery Server
# 2. Config Server
# 3. API Gateway & Identity Service
🧪 Testing the APIsOnce running, you can access the Swagger documentation for the Identity Service at:http://localhost:2021/swagger-ui/index.html