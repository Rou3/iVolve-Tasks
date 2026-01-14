
# Lab 6: Managing Docker Environment Variables Across Build and Runtime

This lab demonstrates how to manage **environment variables** in Docker for different stages: **development**, **staging**, and **production**, using Python and Flask.

---

## 1️⃣ Clone the Application Code
```bash
git clone https://github.com/Ibrahim-Adel15/Docker-3.git
cd Docker-3
````

---

## 2️⃣ Create the Dockerfile

Create a file named `Dockerfile` with the following content:

```dockerfile
# Base image
FROM python:3.11-slim

# Set working directory
WORKDIR /app
COPY . /app

# Install Flask
RUN pip install --no-cache-dir flask

# Environment variables for production
ENV APP_MODE=production
ENV APP_REGION=canada-west

# Expose application port
EXPOSE 8080

# Run the application
CMD ["python", "app.py"]
```

---

## 3️⃣ Build the Docker Image

```bash
docker build -t myflaskapp .
```

---

## 4️⃣ Run Containers with Different Environment Variables

### Development Container

Set variables directly in the command:

```bash
docker run -d -p 8081:8080 \
  -e APP_MODE=development \
  -e APP_REGION=us-east \
  myflaskapp
```

### Staging Container

1. Create `staging.env` file:

```
APP_MODE=staging
APP_REGION=us-west
```

2. Run using the env file:

```bash
docker run -d --env-file staging.env -p 8082:8080 myflaskapp
```

### Production Container

Uses variables defined in Dockerfile:

```bash
docker run -d -p 8083:8080 myflaskapp
```

---

## 5️⃣ Ports & Environment Variables Summary

| Environment | Port | Variables                                   |
| ----------- | ---- | ------------------------------------------- |
| Development | 8081 | APP_MODE=development, APP_REGION=us-east    |
| Staging     | 8082 | APP_MODE=staging, APP_REGION=us-west        |
| Production  | 8083 | APP_MODE=production, APP_REGION=canada-west |

---

## ✅ Notes & Tips

* Use `--env-file` to manage multiple environment variables easily.
* Dockerfile `ENV` is useful for **default or production variables**.
* Make sure host ports **do not conflict** when running multiple containers.
* This setup allows you to **run multiple containers simultaneously** with different configurations.

---
