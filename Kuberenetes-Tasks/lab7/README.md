# Lab 16: Kubernetes Init Container for Pre-Deployment Database Setup

## 🎯 Objective

In this lab, you will learn how to use an **Init Container** in Kubernetes to perform database setup before a Node.js application starts.

Specifically, we will:

* Create a MySQL database `ivolve`.
* Create a user with full privileges on `ivolve`.
* Pass DB credentials using **ConfigMap** and **Secret**.
* Verify the DB and user exist with the expected privileges.

---

## 🛠 Concepts Covered

* Init Containers
* ConfigMap & Secret
* Persistent Volume Claim
* Deployment update
* MySQL in Kubernetes

---

## ✅ Prerequisites

* Kubernetes cluster (Minikube, Kind, or any cluster)
* `kubectl` configured
* MySQL 5.7 Docker image
* Node.js Docker image (placeholder if app not ready)

---

## 1️⃣ Setup Secrets and ConfigMap

### Secret (`mysql-secret.yaml`)

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: mysql-secret
type: Opaque
stringData:
  MYSQL_ROOT_PASSWORD: rootpassword
  MYSQL_DATABASE: ivolve
  MYSQL_USER: ivolveuser
  MYSQL_PASSWORD: ivolvepass
```

### ConfigMap (`nodejs-config.yaml`)

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: nodejs-config
data:
  NODE_ENV: development
```

Apply them:

```bash
kubectl apply -f mysql-secret.yaml
kubectl apply -f nodejs-config.yaml
```

---

## 2️⃣ MySQL Deployment and Service

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mysql
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - name: mysql
        image: mysql:5.7
        env:
        - name: MYSQL_ROOT_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: MYSQL_ROOT_PASSWORD
        ports:
        - containerPort: 3306
---
apiVersion: v1
kind: Service
metadata:
  name: mysql-service
spec:
  selector:
    app: mysql
  ports:
  - port: 3306
    targetPort: 3306
  type: ClusterIP
```

Apply:

```bash
kubectl apply -f mysql-deployment.yaml
kubectl get pods,svc
```

---

## 3️⃣ Node.js Deployment with Init Container

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nodejs-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: nodejs
  template:
    metadata:
      labels:
        app: nodejs
    spec:
      initContainers:
      - name: init-mysql
        image: mysql:5.7
        command:
          - sh
          - -c
          - |
            echo "Waiting for MySQL to be ready..."
            until mysql -h $DB_HOST -u $MYSQL_ROOT_USER -p$MYSQL_ROOT_PASSWORD -e "SELECT 1" >/dev/null 2>&1; do
              echo "MySQL not ready yet... retrying in 5s"
              sleep 5
            done
            echo "Creating database and user..."
            mysql -h $DB_HOST -u $MYSQL_ROOT_USER -p$MYSQL_ROOT_PASSWORD -e "CREATE DATABASE IF NOT EXISTS $MYSQL_DATABASE;"
            mysql -h $DB_HOST -u $MYSQL_ROOT_USER -p$MYSQL_ROOT_PASSWORD -e "CREATE USER IF NOT EXISTS '$MYSQL_USER'@'%' IDENTIFIED BY '$MYSQL_PASSWORD';"
            mysql -h $DB_HOST -u $MYSQL_ROOT_USER -p$MYSQL_ROOT_PASSWORD -e "GRANT ALL PRIVILEGES ON $MYSQL_DATABASE.* TO '$MYSQL_USER'@'%'; FLUSH PRIVILEGES;"
        env:
        - name: DB_HOST
          value: mysql-service
        - name: MYSQL_ROOT_USER
          value: root
        - name: MYSQL_ROOT_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: MYSQL_ROOT_PASSWORD
        - name: MYSQL_DATABASE
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: MYSQL_DATABASE
        - name: MYSQL_USER
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: MYSQL_USER
        - name: MYSQL_PASSWORD
          valueFrom:
            secretKeyRef:
              name: mysql-secret
              key: MYSQL_PASSWORD
      containers:
      - name: nodejs
        image: node:18-alpine
        command: ["sleep", "3600"]
        ports:
        - containerPort: 3000
        envFrom:
        - configMapRef:
            name: nodejs-config
```

Apply:

```bash
kubectl apply -f nodejs-deployment.yaml
kubectl get pods
```
<img width="1038" height="227" alt="lab7-kstart" src="https://github.com/user-attachments/assets/8056f57a-9ad1-4b3e-956f-82feac8abad0" />

<img width="1078" height="116" alt="lab7-k21" src="https://github.com/user-attachments/assets/cda99f48-c76e-471e-a6bd-3a3259bf2114" />

---

## 4️⃣ Verify Database and User

```bash
kubectl run -it --rm mysql-client --image=mysql:5.7 --env="MYSQL_PWD=rootpassword" -- bash
```

Inside the pod:

```sql
mysql -h mysql-service -u root -p
# password: rootpassword
SHOW DATABASES;
SELECT User, Host FROM mysql.user;
```

You should see:

* `ivolve` database
* `ivolveuser` with privileges on `ivolve`
<img width="1062" height="528" alt="lab7-kfinall" src="https://github.com/user-attachments/assets/1bac2d6d-2a8e-4710-ad54-68f122289e26" />

---

## ✅ Notes

* Init container ensures DB setup happens **before Node.js starts**.
* Retry loop prevents CrashLoopBackOff if MySQL is not ready immediately.
* `DB_HOST` must match the MySQL Service name (`mysql-service`).

