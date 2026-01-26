# Lab 18: Control Pod-to-Pod Traffic via NetworkPolicy

This lab demonstrates how to **control pod-to-pod communication** in Kubernetes using a **NetworkPolicy**.  
The goal is to **restrict access to the MySQL pods** so that only specific application pods can connect.

---

## 📌 NetworkPolicy Specification

- **Name:** `allow-app-to-mysql`  
- **Pod Selector:** Pods with label `app=mysql`  
- **Policy Types:** `Ingress` only  
- **Ingress Rules:**  
  - Allow traffic **only from application pods** (e.g., `app=alpine-app`)  
  - Restrict access to **port 3306** (MySQL default port)

---

## 🗂️ Lab Resources

### 1️⃣ MySQL Deployment
- Pods labeled `app=mysql`  
- Exposes port `3306` via ClusterIP service  
- User for testing: `ivolve_user` with password `ivolve_pass`  

### 2️⃣ Application Pods
- Pods labeled `app=alpine-app`  
- Lightweight Alpine pods used to test connectivity to MySQL  

### 3️⃣ NetworkPolicy
- Name: `allow-app-to-mysql`  
- Allows only `alpine-app` pods to access MySQL pods on port 3306  
- Blocks all other pods

---

## 🚀 How to Run

1. Apply MySQL deployment and service:

```bash
kubectl apply -f mysql-deployment.yaml
````

2. Apply application pods (Alpine):

```bash
kubectl apply -f nodejs-deployment.yaml
```

3. Apply NetworkPolicy:

```bash
kubectl apply -f networkpolicy.yaml
```
<img width="932" height="242" alt="lab9-k1" src="https://github.com/user-attachments/assets/2f515d2d-bc72-454a-a8ff-dc571c6bf452" />
<img width="928" height="201" alt="lab9-k2" src="https://github.com/user-attachments/assets/62b1b5d7-5cfc-41de-995c-7b9c83f545a7" />


4. Verify pods are running:

```bash
kubectl get pods
kubectl get svc
```
<img width="930" height="112" alt="lab9-final" src="https://github.com/user-attachments/assets/130a2796-d9a1-45ac-b2b7-97f55b92dd36" />
<img width="926" height="112" alt="lab9-kff" src="https://github.com/user-attachments/assets/c67a93d1-eb9a-47ef-8726-ec28c0ceee41" />


## ✅ Expected Behavior

* Only `alpine-app` pods can connect to MySQL pods on port 3306
* All other pods **cannot** access MySQL pods

---

## ⚡ Notes

* Ensure your cluster supports **NetworkPolicy** (e.g., Calico)
* NetworkPolicy does **not** restrict outgoing traffic by default
* MySQL user must allow connections from any host (`'%'`)

---
