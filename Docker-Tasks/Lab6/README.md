# Lab 6: Managing Docker Environment Variables Across Build and Runtime

This lab demonstrates how to manage **environment variables** in Docker for different stages: **development**, **staging**, and **production**, using Python and Flask.

---

## 1️⃣ Clone the Application Code
```bash
git clone https://github.com/Ibrahim-Adel15/Docker-3.git
cd Docker-3
````
<img width="807" height="175" alt="lab6-1" src="https://github.com/user-attachments/assets/d72624b0-330f-4617-bbb0-98f776a15f3c" />
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
<img width="465" height="190" alt="lab6-2" src="https://github.com/user-attachments/assets/3405cf17-b697-4e66-935f-3790a9aaea81" />
---

## 3️⃣ Build the Docker Image

```bash
docker build -t myflaskapp .
```
<img width="821" height="70" alt="lab6-3" src="https://github.com/user-attachments/assets/e586a185-cc5a-4f8a-8eba-e44e089d5d5b" />
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
<img width="811" height="286" alt="lab6-4" src="https://github.com/user-attachments/assets/2bb74e7d-59b3-4a7d-9fcf-43e481e72e0d" />

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
<img width="806" height="247" alt="lab6-5" src="https://github.com/user-attachments/assets/a8198836-7e6c-4d90-a58f-1449a4ce5009" />

### Production Container

Uses variables defined in Dockerfile:

```bash
docker run -d -p 8083:8080 myflaskapp
```
<img width="810" height="160" alt="lab6-6" src="https://github.com/user-attachments/assets/4d2716e1-e299-4b54-bc1f-14726bd96bce" />
---

## 5️⃣ Ports & Environment Variables Summary

| Environment | Port | Variables                                   |
| ----------- | ---- | ------------------------------------------- |
| Development | 8081 | APP_MODE=development, APP_REGION=us-east    |
| Staging     | 8082 | APP_MODE=staging, APP_REGION=us-west        |
| Production  | 8083 | APP_MODE=production, APP_REGION=canada-west |

---

