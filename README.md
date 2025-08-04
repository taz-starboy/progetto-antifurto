# 📡 IoT Alarm System Project

## 🔍 What This Project Does

The **IoT Alarm System Project** is a smart security system based on microservices, built with Java and Spring Boot. It receives events from sensors (e.g., smoke, motion), stores them in a MySQL database, and generates custom notifications delivered via WhatsApp or email using AI-generated content (powered by Mistral via OpenRouter).

## 💡 Why It’s Useful

- Simulates a real-world IoT security system for testing, demos, or portfolio use.
- Demonstrates a practical microservices architecture using **Spring Boot**, **Docker**, and **CI/CD**.
- Includes integration with third-party APIs like Twilio and OpenRouter.

## 🚀 Getting Started

### ✅ Prerequisites
- Java 17
- Maven
- Docker and Docker Compose
- (Optional) Postman for testing APIs

### ▶️ Running the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/progetto-antifurto.git
   cd progetto-antifurto
   ```

2. Start the services with Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. Use Postman to test the endpoints (see [Postman Collection](#-postman-collection)).

## 🧩 Architecture Overview

```
[IoT Sensor]
     |
     v
[antifurto-service] --> [MySQL]
        |
        v
[notification-service] --> [AI + WhatsApp/Email]
```

## 🧪 API Endpoints

### 🔹 antifurto-service (`localhost:8080`)
#### `GET /events`
- Returns all sensor events stored in the database.

#### `POST /events`
- Saves a sensor event and forwards it to `notification-service`.

**Request JSON:**
```json
{
  "sensorType": "smoke",
  "location": "garage",
  "timeStamp": "2025-08-03T11:25:00",
  "destinationType": "whatsapp",
  "destination": "+391234567890"
}
```

- `destinationType`: `"email"` or `"whatsapp"`
- `destination`: Valid email address or phone number with country code

### 🔹 notification-service (`localhost:8081`)
#### `POST /notifications`
- Directly generates and sends a notification using the provided data.
- Accepts the same JSON structure as `POST /events`.

## 🐳 Docker & Services

- Both microservices are containerized with **Docker**.
- All components (MySQL, antifurto-service, notification-service) are orchestrated via `docker-compose`.
- A **wait-for script** ensures antifurto-service waits for MySQL to be ready before starting.

## ⚙️ Continuous Integration

- Configured with **GitHub Actions** in:

```
.github/workflows/progetto-antifurto-ci.yml
```

- Triggers build and tests on every push to the `main` branch.

## 📬 Postman Collection

A Postman collection is available with the following requests:
- `GET /events`
- `POST /events`
- `POST /notifications`

(Located in [`docs/progetto-antifurto-postman-collection.json`](docs/progetto-antifurto-postman-collection.json))

## 📦 Tech Stack

| Technology           | Purpose                         |
|----------------------|----------------------------------|
| Java 17 (Temurin)    | Language & runtime              |
| Spring Boot 3.5.3    | Backend framework               |
| MySQL                | Relational database             |
| Docker & Compose     | Containerization & orchestration|
| Twilio SDK 10.9.2    | WhatsApp message delivery       |
| OpenRouter (Mistral) | AI-generated message content    |
| GitHub Actions       | CI/CD pipeline                  |

## ✅ Project Status

- [x] Microservices tested locally
- [x] MySQL persistence working
- [x] AI notification generation integrated
- [x] WhatsApp/email message delivery working
- [x] Docker Compose fully configured
- [x] CI/CD working with GitHub Actions

## 👤 Contact

- Name: Marcos Bendezu
- Email: taz23@hotmail.it