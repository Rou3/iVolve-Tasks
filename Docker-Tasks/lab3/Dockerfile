# Use Maven + Java 17 image
FROM maven:3.9.0-eclipse-temurin-17

# Create a working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . /app

# Build the application using Maven
RUN mvn package

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]

