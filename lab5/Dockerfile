# -------- Stage 1: Build the app with Maven --------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# -------- Stage 2: Run the app with Java --------
FROM eclipse-temurin:17-jdk

WORKDIR /app
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar ./demo.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "demo.jar"]

