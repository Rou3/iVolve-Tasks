# Lab 6: Managing Docker Environment Variables Across Build and Runtime

This lab demonstrates how to manage environment variables in Docker during **build time** and **runtime** using Python and Flask.

---

## **1. Clone the Application Code**
```bash
git clone https://github.com/Ibrahim-Adel15/Docker-3.git
cd Docker-3


2. Dockerfile

Create a Dockerfile with the following content:

# Use Python 3.11 slim image
FROM python:3.11-slim

# Set working directory
WORKDIR /app
COPY . /app

# Install Flask
RUN pip install --no-cache-dir flask

# Set environment variables for production
ENV APP_MODE=production
ENV APP_REGION=canada-west

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["python", "app.py"]

3. Build Docker Image
docker build -t myflaskapp .

4. Run Containers with Different Environment Variables
i. Development Container

Run with environment variables directly in the command:

docker run -d -p 8081:8080 -e APP_MODE=development -e APP_REGION=us-east myflaskapp

ii. Staging Container

Create a .env file (e.g., staging.env):

APP_MODE=staging
APP_REGION=us-west


Run using the env file:

docker run -d --env-file staging.env -p 8082:8080 myflaskapp

iii. Production Container

Uses environment variables defined in the Dockerfile:

docker run -d -p 8083:8080 myflaskapp
