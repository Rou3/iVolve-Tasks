# This lab demonstrates how to use multi-stage Docker builds to create a lightweight image.

**Steps:

1. Clone the same Spring Boot project.

3. Use Maven image for build stage.

4. Use Java image for runtime stage.

5. Build, run, and test the application.

**-Dockerfile (Multi-Stage):-

  steps:
   FROM maven:3.9.0-eclipse-temurin-17 AS build
   WORKDIR /app
   COPY . .
   RUN mvn package

   FROM eclipse-temurin:17-jdk
   WORKDIR /app
   COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
   EXPOSE 8080
   CMD ["java", "-jar", "app.jar"]

**Build and run:

  -docker build -t app3 .
  -docker run -d -p 8080:8080 --name container3 app3


This lab shows how multi-stage builds reduce the final image size and improve performance.
