# Energy Management System

A distributed energy management platform designed for real-time monitoring and administration of devices and energy consumption. Built using a microservice architecture with Spring Boot, Vue 3, Docker, RabbitMQ, WebSockets, and PostgreSQL.

## Features

### Functional Requirements

- Role-based authentication (Admin / Client)
- Admin:
  - Full CRUD operations for users and devices
  - Assign devices to clients
- Clients:
  - View assigned devices
  - Restricted access to unauthorized pages
- Real-time monitoring:
  - Energy consumption tracking
  - Asynchronous alerts for consumption threshold breaches
- Chat system:
  - Real-time messaging between clients and admins
  - Notifications for typing and seen status

### Non-Functional Requirements

- Microservice-based architecture:
  - User Management
  - Device Management
  - Monitoring
  - Chat
- JWT-based authentication and authorization
- Communication via RabbitMQ and WebSockets

## Architecture Overview

- **Frontend**: Vue 3
- **Backend**: Spring Boot
- **Databases**: PostgreSQL (per microservice)
- **Messaging**: RabbitMQ
- **Real-time communication**: WebSockets with STOMP

## Project Structure

```
Assignment 1/
├── chat-microservice/
├── devices-microservice/
├── usermanagement-microservice/
│   └── docker-compose.yml
├── monitoring-microservice/
├── management-frontend/
└── device-producer/
    ├── producer.py
    └── sensor.csv
```

## Getting Started

### Docker Deployment

1. Navigate to `usermanagement-microservice` and run:
   ```bash
   docker-compose up
   ```

2. In another terminal, go to `management-frontend`:
   ```bash
   npm install
   npm run serve
   ```

3. Access the application at:
   ```
   http://localhost:3000
   ```

## Ports Used

| Service                 | Port   |
|------------------------|--------|
| User Management        | 8080   |
| Device Management      | 8081   |
| Monitoring             | 8082   |
| Chat                   | 8084   |
| Frontend               | 3000   |
| User Database          | 5433   |
| Device Database        | 5434   |
| Monitoring Database    | 5435   |
| RabbitMQ               | 5672   |

## Simulating Device Data

1. Run the frontend and register devices.
2. Execute the data producer script:
   ```bash
   python3 producer.py
   ```
   This sends data from `sensor.csv` to RabbitMQ at regular intervals.

## Technologies Used

- Vue 3
- Java Spring Boot
- Spring Security
- PostgreSQL
- RabbitMQ
- WebSockets with STOMP
- JWT Authentication
- Docker and Docker Compose

## Notifications

- Users receive asynchronous notifications when devices exceed configured energy limits.
- Real-time chat includes typing indicators and read receipts.

## Demo Credentials

- **Admin**
  - Username: `admin`
  - Password: `admin`

You may register a new client user from a different browser and test the chat and monitoring features.
