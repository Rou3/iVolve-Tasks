# Lab 17: Pod Resource Management with CPU and Memory Requests & Limits

## 📌 Objective

The goal of this lab is to practice **Pod Resource Management** in Kubernetes by configuring **CPU and Memory requests and limits** for a Deployment and verifying them at runtime.

---

## 🛠️ Lab Requirements

Update an existing Deployment to:

### Resource Requests

* **CPU:** 0.5 vCPU
* **Memory:** 1Gi

### Resource Limits

* **CPU:** 1 vCPUs
* **Memory:** 2Gi

### Verification

* Use `kubectl describe pod` to verify requests and limits
* Use `kubectl top pod` to monitor real-time resource usage

---

## 📂 Files Used

* `nodejs-deployment.yaml`

---

## 📄 Deployment YAML

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
      tolerations:
      - key: "node"
        operator: "Equal"
        value: "worker"
        effect: "NoSchedule"
      containers:
      - name: alpine
        image: alpine:latest
        command: ["sh", "-c", "sleep 3600"]
        resources:
          requests:
            cpu: "0.5"
            memory: 1Gi
          limits:
            cpu: "1"
            memory: 2Gi
```

---

## ▶️ Steps to Run

### 1️⃣ Apply the Deployment

```bash
kubectl apply -f nodejs-deployment.yaml
```
---

### 2️⃣ Verify Pod Status

```bash
kubectl get pods
```

Expected output:

```text
nodejs-app-xxxxx   1/1   Running
```

---

### 3️⃣ Verify Resource Requests & Limits

```bash
kubectl describe pod <POD_NAME>
```

Look for:

```text
Requests:
  cpu:     0.5
  memory:  1Gi
Limits:
  cpu:     1
  memory:  2Gi
```

---

### 4️⃣ Monitor Resource Usage

> Metrics Server must be enabled

```bash
kubectl top pod
```

Sample output:

```text
NAME           CPU(cores)   MEMORY(bytes)
nodejs-app     10m          30Mi
```

---

## 🧠 Key Concepts

* **Requests** define the minimum resources required by a Pod
* **Limits** define the maximum resources a Pod can consume
* If requests exceed node capacity, the Pod remains in `Pending` state
* CPU limits cause throttling, memory limits may cause OOMKill

---

## ✅ Lab Status

✔ Deployment created successfully
✔ Resource requests and limits applied
✔ Resources verified using `kubectl describe`
✔ Usage monitored using `kubectl top`

---

## ✨ Notes

* `replicas` set to **1** to avoid insufficient CPU issues in Minikube
* `tolerations` added to allow scheduling on tainted worker nodes

---
