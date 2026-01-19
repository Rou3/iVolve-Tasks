# Lab 3: Run Java Spring Boot App in a Container

This lab demonstrates running a Java Spring Boot application inside a Docker container.

## Prerequisites

- Docker installed
- Maven installed (optional if using Maven base image)

## Clone the Application

```bash
git clone https://github.com/Ibrahim-Adel15/Docker-1.git
cd Docker-1
````

## Dockerfile

Create a `Dockerfile` with the following content:

```dockerfile
# Use Maven base image with Java 17
FROM maven:3.9.0-eclipse-temurin-17

# Set working directory
WORKDIR /app

# Copy application source code
COPY . .

# Build the application
RUN mvn package

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]
```

## Build Docker Image

```bash
docker build -t app1 .
```

## Run Container

```bash
docker run -d --name container1 -p 8080:8080 app1
```

## Test the Application

Open your browser or use curl:

```bash
curl http://localhost:8080/
```

## Stop and Remove the Container

```bash
docker stop container1
docker rm container1
```
