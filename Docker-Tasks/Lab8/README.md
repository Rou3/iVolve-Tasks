# Lab 8: Custom Docker Network for Microservices

This lab demonstrates creating a custom Docker network for microservices and verifying container communication.

## Prerequisites

- Docker installed

## Clone the Application

```bash
git clone https://github.com/Ibrahim-Adel15/Docker5.git
cd Docker5
````
<img width="815" height="50" alt="lab8-1" src="https://github.com/user-attachments/assets/29d7e606-27fc-4265-99d1-86f6ef929942" />

## Dockerfiles

### Frontend Dockerfile

```dockerfile
# Frontend Dockerfile
FROM python:3.11-slim
WORKDIR /app
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt
COPY app.py .
EXPOSE 5000
CMD ["python", "app.py"]
```

### Backend Dockerfile

```dockerfile
# Backend Dockerfile
FROM python:3.11-slim
WORKDIR /app
RUN pip install flask
COPY app.py .
EXPOSE 5000
CMD ["python", "app.py"]
```

## Build Docker Images

```bash
docker build -t frontend-image ./frontend
docker build -t backend-image ./backend
```
<img width="1062" height="49" alt="lab9-3" src="https://github.com/user-attachments/assets/f4183b6d-f83c-4af5-9047-6b38ccf9c46c" />
<img width="1066" height="116" alt="lab9-4" src="https://github.com/user-attachments/assets/2e661cdf-34d8-4d65-9191-0eca37d5e3d1" />

## Create Custom Network

```bash
docker network create ivolve-network
```
<img width="806" height="75" alt="lab8-5" src="https://github.com/user-attachments/assets/1314fbe3-4f80-4c78-9497-f90226f0a7fd" />

## Run Containers

### Backend Container on Custom Network

```bash
docker run -d --name backend --network ivolve-network backend-image
```
<img width="810" height="65" alt="lab8-6" src="https://github.com/user-attachments/assets/81ebff56-0983-4cc6-82c9-95e3a7b8fe03" />

### Frontend Container on Custom Network

```bash
docker run -d --name frontend1 --network ivolve-network frontend-image
```
<img width="807" height="73" alt="lab8-7" src="https://github.com/user-attachments/assets/25992a0a-2983-4cb4-8e21-db880cf1fac1" />

### Another Frontend Container on Default Network

```bash
docker run -d --name frontend2 frontend-image
```

## Verify Communication

* **frontend1** should be able to communicate with **backend** via `ivolve-network`.
* **frontend2** cannot communicate with **backend** as it is on the default network.

```bash
docker exec -it frontend1 ping backend
docker exec -it frontend2 ping backend # should fail
```
<img width="806" height="73" alt="lab8-9" src="https://github.com/user-attachments/assets/6151f4d8-043e-48b4-b54f-51de31017344" />
<img width="810" height="94" alt="lab8-10" src="https://github.com/user-attachments/assets/46be8787-f533-4895-8d6d-fbcbed4deec2" />

## Cleanup

Delete the custom network after testing:

```bash
docker network rm ivolve-network
```
<img width="812" height="247" alt="lab8-11" src="https://github.com/user-attachments/assets/978f57a2-f47e-4f70-9df4-bd9f1ae018cc" />

```

