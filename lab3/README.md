**Lab 3: Java Spring Boot App in Docker

**Steps:

1.Clone the project
  -git clone https://github.com/Ibrahim-Adel15/Docker-1.git
  -cd Docker-1

2.Write Dockerfile
  -steps:
     FROM maven:3.9.0-eclipse-temurin-17
     WORKDIR /app
     COPY . /app
     RUN mvn package
     EXPOSE 8080
     CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]

4.Build Docker Image
  -docker build -t lab3 .

5.Run Docker Container
  -docker run -d -p 8080:8080 --name container1 lab3

6.Test Application
  -curl http://localhost:8080

7.Stop and Remove Container
  -docker stop container1
  -docker rm container1

**Project Structure

Docker-1/
├── Dockerfile
├── pom.xml
├── src/          # Spring Boot source code
└── target/       # Generated JAR
