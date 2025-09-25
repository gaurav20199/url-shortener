# 🔗 URL Shortener

A simple and efficient URL shortener app inspired by **Bitly**. Create short URLs and manage their expiry with ease.  

---

## 🛠️ Tech Stack
- **Backend:** Spring Boot  
- **Frontend:** Thymeleaf  
- **Build Tool:** Maven  

---

## 📁 Project Structure & Design

Since the application is small (mainly **User Management** and **URL Shortener** modules), the package structure is based on **components** instead of functionalities.  

- **Controller** → Manages HTTP requests and responses  
- **Service** → Contains business logic  
- **Repository** → Handles data persistence  

This structure ensures **modularity** and **ease of maintenance**.  

---

## 🚀 Features
- 🔗 **Shorten URLs** → Convert long URLs into short, shareable links  
- ⏳ **Expiry Management** → Set expiration times for shortened URLs  
- 🔒 **Private URL** → Logged-in users can create private URLs not accessible to others  
- 👤 **User Management** → Basic user authentication & management with Spring Security  

---

## ⚙️ Setup & Installation

### Prerequisites
- Java **17** or higher  
- Maven **3.8.1** or higher  

### Steps
```bash
# 1) Clone the repository
git clone https://github.com/gaurav20199/url-shortener.git
cd url-shortener

# 2) Build the application
mvn clean install

# 3) Run the application
mvn spring-boot:run
