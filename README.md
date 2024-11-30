# Davina's Villa Hotel Management System

Welcome to the official repository for Davina's Villa Hotel booking management system.
This project includes a robust backend
to streamline hotel operations and enhance customer experience.

---

## Project Overview

- **Backend**: [Spring Boot](https://spring.io/projects/spring-boot)

This system includes:
- A responsive website for customers to browse and book rooms.
- An admin dashboard for managing bookings, customers, and room availability.
- Integration with third-party APIs for payment and notifications.

---

## Table of Contents

1. [Features](#features)
2. [Installation](#installation)
    - [Backend Setup](#backend-setup)
3. [Usage](#usage)
4. [Project Structure](#project-structure)

---

## Features

### Backend (Spring Boot)
- RESTful API for hotel management.
- Secure user authentication and role-based access.
- Database management for bookings, customers, and rooms.
- Integration with Cloudflare R2 for media storage.

---

## Installation

### Prerequisites
- [Java JDK 17+](https://adoptopenjdk.net/) (for backend)
- [PostgreSQL](https://www.postgresql.org/) (or any preferred database)

### Backend Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/akwaboah/davinas-villa.git
   cd davinas-villa/backend
   Run commands
   Unix systems: ./mvnw spring-boot:run
   windowssystems: mvnw.cmd spring-boot:run

### Usage
- Access the backend API at http://localhost:8080/api.

## Project Structure
```bash
project-root/
├───.gitignore # General rules for the whole project
├───backend # Backend Infrastructure (SpringBoot)
│   ├───.gitignore  # Specific rules for backend
│   ├───.mvn
│   │   └───wrapper
│   └───src  # backend sources directory
│       ├───main
│       │   ├───java
│       │   │   └───com
│       │   │       └───ikanetapps
│       │   │           └───davina
│       │   └───resources
│       │       ├───static
│       │       └───templates
│       └───test
│           └───java
│               └───com
│                   └───ikanetapps
│                       └───davina
└───docs # Directory for general documentation

```