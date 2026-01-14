 Lab 7: Docker Volume and Bind Mount with Nginx

This lab demonstrates how to use **Docker volumes** and **bind mounts** with Nginx to persist logs and serve custom HTML files.

---

## 1️⃣ Create a Docker Volume for Nginx Logs
Create a volume named `nginx_logs`:

```bash
docker volume create nginx_logs
````

Verify the volume exists:

```bash
docker volume ls
docker volume inspect nginx_logs
```

<img width="808" height="243" alt="lab7-1" src="https://github.com/user-attachments/assets/a1ef65ef-26fb-435c-8f28-37fac49822e9" />

---

## 2️⃣ Create a Directory for Bind Mount

Create a directory on the host machine to serve a custom HTML file:

```bash
mkdir -p nginx-bind/html
```

Create `index.html` inside it:

```html
<!-- nginx-bind/html/index.html -->
<!DOCTYPE html>
<html>
<head>
    <title>Bind Mount Test</title>
</head>
<body>
    <h1>Hello from Bind Mount</h1>
</body>
</html>
```
<img width="814" height="88" alt="lab7-2" src="https://github.com/user-attachments/assets/04d7d211-c400-4cba-9723-f7759ae18c0c" />
---

## 3️⃣ Run Nginx Container with Volume and Bind Mount

Run the container:

```bash
docker run -d \
  --name nginx-test \
  -v nginx_logs:/var/log/nginx \
  -v $(pwd)/nginx-bind/html:/usr/share/nginx/html \
  -p 8080:80 \
  nginx
```
<img width="818" height="140" alt="lab7-3" src="https://github.com/user-attachments/assets/833e5c9e-6aea-4cde-9a38-d173b686c6b8" />
---

## 4️⃣ Verify Nginx Page

Check the page from your host machine:

```bash
curl http://localhost:8080
```

You should see:

```
Hello from Bind Mount
```
<img width="814" height="79" alt="lab7-4" src="https://github.com/user-attachments/assets/0f815c13-5d8b-4cc9-995c-0926b271e4f5" />
---

## 5️⃣ Test Live Changes

Edit the `index.html` on your host machine, for example:

```html
<h1>Hello Rawan</h1>
```

Verify changes via curl again:

```bash
curl http://localhost:8080
```
<img width="812" height="80" alt="lab7-5" src="https://github.com/user-attachments/assets/e512bf63-f03f-4729-9db9-57ef105d3e70" />
---

## 6️⃣ Verify Logs in Docker Volume

Check logs stored in the `nginx_logs` volume:

```bash
sudo ls /var/lib/docker/volumes/nginx_logs/_data
```
<img width="812" height="106" alt="lab7-6" src="https://github.com/user-attachments/assets/2c33766b-1f51-48a5-b354-09b469018c68" />
---

## 7️⃣ Delete the Volume

```bash
docker volume rm nginx_logs
```
<img width="814" height="206" alt="lab7-7" src="https://github.com/user-attachments/assets/860db151-4ee3-44c3-8c37-8ee2a05186a3" />
---



