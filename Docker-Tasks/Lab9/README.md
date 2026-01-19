# Lab 9: Containerized Node.js and MySQL Stack Using Docker Compose

This project demonstrates a containerized Node.js application connected to a MySQL database using Docker Compose.

## Prerequisites

- Docker
- Docker Compose
- DockerHub account

## Clone the Application

```bash
git clone https://github.com/Ibrahim-Adel15/kubernets-app.git
cd kubernets-app
````
<img width="1059" height="44" alt="lab9-1" src="https://github.com/user-attachments/assets/b1c48656-d12e-420b-a8e0-2889c476fb14" />

## MySQL Database

The application requires a MySQL database named `ivolve`.

## Docker Compose Setup

Create a `docker-compose.yml` file with the following configuration:

```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "3000:3000"
    environment:
      DB_HOST: db
      DB_USER: root
      DB_PASSWORD: examplepassword
    depends_on:
      - db

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: examplepassword
    volumes:
      - db_data:/var/lib/mysql

volumes:
  db_data:
```
<img width="1052" height="26" alt="lab9-2" src="https://github.com/user-attachments/assets/5377e131-fd20-4f3e-8432-8125e59a4a27" />

## Running the Application

Start the stack with Docker Compose:

```bash
docker-compose up -d
```

Verify that the containers are running:

```bash
docker-compose ps
```
<img width="1062" height="49" alt="lab9-3" src="https://github.com/user-attachments/assets/b421723b-935e-4bc6-9234-0f8b291e27f3" />

## Verifications

* **Health check:**

  ```bash
  curl http://localhost:3000/health
  ```

* **Readiness check:**

  ```bash
  curl http://localhost:3000/ready
  ```
<img width="1066" height="116" alt="lab9-4" src="https://github.com/user-attachments/assets/52c8c7c4-6679-425d-a636-1b4f274f7bb6" />

* **Application logs:**

  ```bash
  ls app/logs/
  tail -f app/logs/<logfile>
  ```
<img width="1069" height="159" alt="lab9-5" src="https://github.com/user-attachments/assets/477870d2-0af9-4630-9819-7aed8238b798" />

## Pushing Docker Image to DockerHub

1. Build the Docker image:

```bash
docker build -t <your-dockerhub-username>/kubernets-app:latest .
```

2. Login to DockerHub:

```bash
docker login
```

3. Push the image:

```bash
docker push <your-dockerhub-username>/kubernets-app:latest
```
<img width="1069" height="111" alt="lab9-6" src="https://github.com/user-attachments/assets/11272b51-6a9e-48f0-8ee5-2948cb6ab85b" />
<img width="888" height="47" alt="lab9-7" src="https://github.com/user-attachments/assets/e7b986ff-ea9b-4895-958a-291930eb811d" />

---



