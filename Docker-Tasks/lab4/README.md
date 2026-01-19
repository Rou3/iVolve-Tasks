# Lab 4: Run Java Spring Boot App in a Container (JAR Deployment)

This lab demonstrates running a pre-built Java Spring Boot JAR inside a Docker container using a Java base image.

## Prerequisites

- Docker installed
- Java JDK installed (for building the JAR)

## Clone the Application

```bash
git clone https://github.com/Ibrahim-Adel15/Docker-1.git
cd Docker-1
````

## Build the Application

```bash
mvn package
```

The JAR file will be located at:

```bash
target/demo-0.0.1-SNAPSHOT.jar
```

## Dockerfile

Create a `Dockerfile` with the following content:

```dockerfile
# Use Java 17 base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
```

## Build Docker Image

```bash
docker build -t app2 .
```

> Note: Check the image size using:

```bash
docker images
```

## Run Container

```bash
docker run -d --name container2 -p 8080:8080 app2
```

## Test the Application

Open your browser or use curl:

```bash
curl http://localhost:8080/
```

## Stop and Remove the Container

```bash
docker stop container2
docker rm container2
```
