# Shop Dev App

## Overview

Shop Dev App is a sample e-commerce application built with a Spring Boot backend and a React frontend. The application allows users to browse products, add them to a cart, and proceed to checkout.

## Prerequisites

- Java 17
- Node.js
- Docker
- Maven

## Setup

### Backend

1. Navigate to the project root directory.
2. Create a `.env` file and configure your database settings.
3. Build the project using Maven:
  ```sh
  mvn clean install
  ```
4. Run the application:
  ```sh
  mvn spring-boot:run
  ```
5. The backend will be accessible at `http://localhost:8080`.

### Frontend

1. Navigate to the `frontend` directory.
2. Install the dependencies:
  ```sh
  npm install
  ```
3. Start the development server:
  ```sh
  npm start
  ```
4. The frontend will be accessible at `http://localhost:3000`.

### Docker

To run the application using Docker, navigate to the project root directory and run:
  ```sh
  docker-compose up
  ```
The application will be accessible at `http://localhost:3000`.

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For any questions, feel free to contact me at [giahung112358@gmail.com](giahung112358@gmail.com).
