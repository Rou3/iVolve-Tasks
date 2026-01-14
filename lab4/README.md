# Lab 4: Run Java Spring Boot App in a Container

This lab demonstrates how to run a **Java Spring Boot application** inside a Docker container using **Java 17**.

In this lab, you first clone the project, build the application, create a Docker image, run it in a container, test the application, and finally stop and remove the container.

**Steps:**

1. Clone the project:
   ```bash
   -git clone https://github.com/Ibrahim-Adel15/Docker-1.git
   -cd Docker-1

2. Build the application using Maven:
   -mvn clean package

3. Write a Dockerfile:
   -Steps:

   # Use Java 17 base image
     FROM eclipse-temurin:17-jdk
   # Create work directory
     WORKDIR /app
   # Copy the JAR file into the container
     COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar
   # Expose port 8080
     EXPOSE 8080
   # Run the application
     CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]

4. Build the Docker image:
   -docker build -t app2 .

5. Run the container:
   -docker run -d -p 8080:8080 --name container2 app2

6. Test the application:
   -curl http://localhost:8080

7. Stop and remove the container:
   -docker stop container2 
   -docker rm container2


Project structure:

Docker-1/
├── Dockerfile
├── pom.xml
├── src/          # Spring Boot source code
└── target/       # Generated JAR
