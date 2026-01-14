# Lab 1: Java Application with Gradle

This lab uses Java 17 and Gradle for managing dependencies and building the project. The archiveBaseName in build.gradle defines the name of the generated JAR file.

This lab demonstrates how to build, test, and run a simple Java application using **Gradle

1. Clone the project:
   ```bash
   -git clone https://github.com/Ibrahim-Adel15/build1.git
   -cd build1

2. Install Gradle (if not installed):
   -sudo apt install gradle -y
   -gradle -v

3. Run unit tests to make sure the code works:
   -gradle test

4. Build the application and generate the JAR file:
   -gradle clean build

5. Run the application 
   -java -jar build/libs/ivolve-app.jar

Project structure:

build1/
├── build.gradle       # Gradle build file
├── settings.gradle
├── src/               # Java source code
└── build/             # Compiled JAR file


# Lab 2: Building and Packaging Java Application with Maven
In this lab, Maven is used to build and package a Java application.
Steps:
**Install Maven.**
1.Clone the source code from:
    https://github.com/Ibrahim-Adel15/build2.git

2.Run unit tests.

3.Build the application. 
  Commands:
    mvn test
    mvn package

4.The generated artifact will be:
    target/hello-ivolve-1.0-SNAPSHOT.jar

5.Run the application:
    java -jar target/hello-ivolve-1.0-SNAPSHOT.jar

# Lab 3: Run Java Spring Boot App in a Container (Maven Build)
This lab focuses on running a Spring Boot application inside a Docker container using a Maven base image.

Steps:
1.Clone the project:
https://github.com/Ibrahim-Adel15/Docker-1.git

2.Write a Dockerfile using Maven and Java 17.

3.Build the Docker image.

4.Run the container and test the application.

**Dockerfile:**
FROM maven:3.9.0-eclipse-temurin-17
WORKDIR /app
COPY . /app
RUN mvn package
EXPOSE 8080
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]

Build and run:
docker build -t app1 .
docker run -d -p 8080:8080 --name container1 app1

Test:
curl http://localhost:8080

# Lab 4: Run Java Spring Boot App in a Container (JAR Only)

In this lab, the application is built first, then only the JAR file is used inside the Docker image.

Steps:

1. Build the application using Maven.

2. Write a Dockerfile with Java 17 base image.

3. Copy the JAR file into the container.

4. Run and test the application.

**Dockerfile:**
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

Build and run:
docker build -t app2 .
docker run -d -p 8080:8080 --name container2 app2

Note: The image size is smaller than Lab 3.

# Lab 5: Multi-Stage Build for Java Spring Boot App

This lab demonstrates how to use multi-stage Docker builds to create a lightweight image.

Steps:

1.Clone the same Spring Boot project.

2.Use Maven image for build stage.

3.Use Java image for runtime stage.

4.Build, run, and test the application.

Dockerfile (Multi-Stage):

FROM maven:3.9.0-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn package

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

Build and run:
docker build -t app3 .
docker run -d -p 8080:8080 --name container3 app3


**This lab shows how multi-stage builds reduce the final image size and improve performance.**
