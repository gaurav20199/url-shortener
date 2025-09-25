A simple and efficient URL shortener app inspired by Bitly. Create short URLs and manage their expiry with ease.

🛠️ Tech Stack
Backend: Spring Boot
Frontend: Thymeleaf
Build Tool: Maven

📁 Project Structure and Design
Package Structure-> Since the application is not quite big, typically containing a user management module and url shorterner module. That's why package structure is not based on functionality and is rather based on component.
Controller: Manages HTTP requests and responses.
Service: Contains business logic.
Repository: Handles data persistence.
This structure ensures modularity and ease of maintenance.

🚀 Features
Shorten URLs: Convert long URLs into short, shareable links.
Expiry Management: Set expiration times for shortened URLs.
Private URL: Logged in User can create Private URL that will not be accessible to everyone.
User Management: Basic user authentication and management.

⚙️ Setup & Installation
Prerequisites
Java 17 or higher
Maven 3.8.1 or higher

Steps
1) Clone the repository:
git clone https://github.com/gaurav20199/url-shortener.git
cd url-shortener

2) Build the application:
mvn clean install

3) Run the application:
mvn spring-boot:run


Access the application at http://localhost:8080.

📄 License

This project is licensed under the MIT License.
