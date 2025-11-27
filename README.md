# ✈️ Sky-Booking - Flight Reservation System

A comprehensive flight booking web application built with Spring Boot and Thymeleaf. This system allows users to search, filter, and book flights while providing administrators with complete management capabilities.

![Java](https://img.shields.io/badge/Java-11+-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.2.5-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-3.3.7-purple)

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Database Setup](#-database-setup)
- [Running the Application](#-running-the-application)
- [Docker Deployment](#-docker-deployment)
- [Project Structure](#-project-structure)
- [API Endpoints](#-api-endpoints)
- [User Roles](#-user-roles)
- [Internationalization](#-internationalization)

## ✨ Features

### For Passengers (PUTNIK)
- **Flight Search & Filtering**
  - Search flights by departure/destination (airport code, city, or country)
  - Filter by departure date with flexible date range option (±2 days)
  - Filter by number of available seats
  - Sort results by departure, destination, or departure time (ascending/descending)
  - Automatic connection flight suggestions when no direct flights are available

- **Booking & Reservations**
  - Interactive seat selection with visual seat map
  - Shopping cart functionality with cookie-based persistence
  - Multiple ticket booking in a single reservation
  - View reservation history in user profile
  - Passenger details (name, passport number) for each ticket

- **Loyalty Program**
  - Request loyalty card membership
  - Earn loyalty points on purchases (1 point per 30,000 RSD spent)
  - Redeem points for discounts (7% discount per point, up to 100%)
  - Receive 5 bonus points when flights are cancelled

- **Wishlist**
  - Add flights to personal wishlist
  - Quick access to wishlisted flights from profile
  - Easy reservation from wishlist

- **User Account**
  - User registration and login
  - "Remember me" functionality with cookies
  - Profile management (edit personal information)
  - View booking history and wishlist

### For Administrators (ADMIN)
- **User Management**
  - View all registered users
  - Filter users by username and role
  - Block/unblock user accounts
  - Approve or reject loyalty card requests

- **Flight Management**
  - Create new flights with validation
  - Edit existing flight details
  - Delete flights (only if no tickets are sold)
  - Cancel flights with reason (automatic loyalty compensation for affected passengers)
  - Filter flights by flight code

- **Promotional Offers**
  - Set promotional discounts (percentage-based)
  - Define promotion validity dates
  - Promotional flights highlighted on homepage

- **Location & Airport Management**
  - Add/edit/delete locations (cities with country and continent)
  - Upload location images
  - Create airports linked to locations

- **Aircraft Management**
  - Add new aircraft with seat configuration (rows × columns)
  - Aircraft seat capacity calculation

- **Statistics & Reports**
  - View ticket sales reports
  - Filter reports by date range
  - Total revenue and ticket count analytics

## 🛠 Tech Stack

### Backend
- **Java 11+** - Programming language
- **Spring Boot 2.2.5** - Application framework
- **Spring MVC** - Web layer
- **Spring JDBC** - Database access
- **Thymeleaf** - Server-side template engine

### Frontend
- **Bootstrap 3.3.7** - CSS framework
- **jQuery 3.2.1** - JavaScript library
- **AOS (Animate On Scroll)** - Animation library

### Database
- **MySQL** - Relational database

### Build & Deployment
- **Maven** - Build tool
- **Docker** - Containerization

## 📋 Prerequisites

- Java 11 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Docker (optional, for containerized deployment)

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/aleksavelickovic/Sky-Booking.git
   cd Sky-Booking
   ```

2. **Navigate to the project directory**
   ```bash
   cd SlozeniOblikVezbi-Projekat
   ```

3. **Configure database connection**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/webprojekat?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Europe/Belgrade
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

## 🗄 Database Setup

1. **Create the database and tables**
   
   Run the SQL script located at:
   ```
   SlozeniOblikVezbi-Projekat/src/main/db/CreateDatabase.sql
   ```
   
   This script creates:
   - `lokacije` - Locations (cities, countries, continents)
   - `korisnici` - Users
   - `aerodromi` - Airports
   - `avioni` - Aircraft
   - `letovi` - Flights
   - `karte` - Tickets
   - `rezervacije` - Reservations
   - `rezervacija_karta` - Reservation-Ticket junction table

2. **Load sample data (optional)**
   
   The `CreateDatabase.sql` script includes sample data for testing.

## ▶️ Running the Application

### Using Maven

```bash
cd SlozeniOblikVezbi-Projekat
mvn clean install
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080/PrviMavenVebProjekat`

> **Note:** The context path `/PrviMavenVebProjekat` is the default configuration. This can be changed in `application.properties` by modifying `server.servlet.contextPath`.

### Using the JAR file

```bash
cd SlozeniOblikVezbi-Projekat
mvn clean package
java -jar target/PrviMavenVebProjekat.jar
```

## 🐳 Docker Deployment

1. **Build the application**
   ```bash
   cd SlozeniOblikVezbi-Projekat
   mvn clean package
   ```

2. **Build Docker image**
   ```bash
   docker build -t sky-booking .
   ```

3. **Run the container**
   ```bash
   docker run -p 8080:8080 sky-booking
   ```

## 📁 Project Structure

```
SlozeniOblikVezbi-Projekat/
├── src/
│   └── main/
│       ├── java/com/ftn/PrviMavenVebProjekat/
│       │   ├── bean/          # Configuration beans
│       │   ├── controller/    # MVC Controllers
│       │   │   ├── AerodromiController.java
│       │   │   ├── AvioniController.java
│       │   │   ├── KorisniciController.java
│       │   │   ├── KorpaKontroller.java
│       │   │   ├── LetoviController.java
│       │   │   └── LokacijeController.java
│       │   ├── model/         # Domain models
│       │   │   ├── Aerodrom.java
│       │   │   ├── Avion.java
│       │   │   ├── Karta.java
│       │   │   ├── Korisnik.java
│       │   │   ├── Let.java
│       │   │   ├── Lokacija.java
│       │   │   └── Rezervacija.java
│       │   ├── repository/    # Data access layer
│       │   └── service/       # Business logic
│       ├── resources/
│       │   ├── static/        # Static assets (CSS, JS, images)
│       │   ├── templates/     # Thymeleaf templates
│       │   ├── application.properties
│       │   ├── messages.properties        # English translations
│       │   └── messages_sr.properties     # Serbian translations
│       └── db/
│           ├── CreateDatabase.sql
│           └── db.sql
├── uploads/                   # Uploaded location images
├── pom.xml
└── Dockerfile
```

## 🔗 API Endpoints

### Public Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/` | Homepage with flight listings |
| POST | `/filter` | Search and filter flights |
| GET | `/korisnici/login` | Login page |
| POST | `/korisnici/login` | Process login |
| GET | `/korisnici/add` | Registration page |
| POST | `/korisnici/add` | Process registration |

### Authenticated User Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/korisnici/profile` | User profile |
| GET | `/korisnici/edit` | Edit profile page |
| POST | `/korisnici/edit` | Update profile |
| GET | `/korisnici/logout` | Logout |
| GET | `/korisnici/loyaltyrequest` | Request loyalty card |
| GET | `/seatoptions` | Seat selection page |
| GET | `/reservation` | Reservation form |
| POST | `/reservation` | Complete reservation |
| GET | `/korpa` | Shopping cart |
| POST | `/korpa/napunikorpu` | Add tickets to cart |
| POST | `/letovi/dodajnalistuzelja` | Add to wishlist |
| GET | `/letovi/ukloniizlistezelja` | Remove from wishlist |

### Admin Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/korisnici/admin` | Admin dashboard |
| GET | `/korisnici` | User management |
| GET | `/korisnici/blockunblock` | Block/unblock user |
| GET | `/korisnici/odobrizahtevzaloyalty` | Approve loyalty request |
| GET | `/korisnici/odbijzahtevzaloyalty` | Reject loyalty request |
| GET | `/lokacije` | Location management |
| GET | `/lokacije/add` | Add location |
| GET | `/lokacije/edit` | Edit location |
| GET | `/lokacije/delete` | Delete location |
| GET | `/aerodromi/add` | Add airport |
| GET | `/avioni/add` | Add aircraft |
| GET | `/letovi/add` | Add flight |
| GET | `/letovi/edit` | Edit flight |
| GET | `/letovi/delete` | Delete flight |
| GET | `/otkazi` | Cancel flight |
| POST | `/letovi/definisiakciju` | Set promotional offer |
| GET | `/letovi/statistics` | View reports |

## 👥 User Roles

### PUTNIK (Passenger)
- Standard user with booking capabilities
- Can manage profile and view reservations
- Access to loyalty program

### ADMIN (Administrator)
- Full system access
- User management capabilities
- Flight and location management
- Access to statistics and reports

### Default Admin Account
```
Username: pera
Password: pera123
```

## 🌍 Internationalization

The application supports multiple languages:
- **English** (default) - `messages.properties`
- **Serbian** - `messages_sr.properties`

Language can be switched via the language selector in the navigation bar.

## 📄 License

This project was developed as part of a Web Programming course project.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request