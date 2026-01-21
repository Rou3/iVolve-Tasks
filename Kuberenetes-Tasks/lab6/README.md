# Lab 15: Node.js Application Deployment with ClusterIP Service

## Objective
Deploy a **Node.js** application on Kubernetes using **Deployment**, **PersistentVolume**, **ConfigMap**, **Secret**, and **ClusterIP Service**.

---

## 1️⃣ Lab Components

### 1. Deployment
- Name: `nodejs-app`
- Replicas: 2
- Image: `node:18-alpine` (official Node.js image)
- Node.js server command:
```javascript
require('http').createServer((req,res)=>res.end('Hello from Node.js!')).listen(3000)
````
<img width="932" height="45" alt="lab6-k1" src="https://github.com/user-attachments/assets/c70a97cc-9297-470d-8d18-0f10f247f3ca" />

* Environment Variables from:

  * ConfigMap: `nodejs-config`
  * Secret: `nodejs-secret`
* Volume Mount:

  * PersistentVolumeClaim: `nodejs-pvc`
  * Mount path inside container: `/usr/src/app/data`
* Toleration for node:

  * `key=node`, `value=worker`, `effect=NoSchedule`

---

### 2. Persistent Volume

* PVC Name: `nodejs-pvc`
* Access Mode: `ReadWriteOnce`
* Storage: 1Gi

---

### 3. ConfigMap

* Name: `nodejs-config`
* Data:

```text
DB_HOST: mysql-service
DB_USER: nodeuser
```
<img width="936" height="159" alt="lab6-k3" src="https://github.com/user-attachments/assets/e913873b-bdc8-42cf-a09b-a51a0eeb808c" />

---

### 4. Secret

* Name: `nodejs-secret`
* Data (base64-encoded):

```text
DB_PASSWORD: <base64-encoded-password>
```
<img width="934" height="65" alt="lab6-k2" src="https://github.com/user-attachments/assets/61fce830-aa4d-472f-b906-4bfa31395e7a" />
<img width="930" height="151" alt="lab6-k4" src="https://github.com/user-attachments/assets/f129b5f6-2c8e-45f1-a1ea-b1cb06e72fcb" />

---

### 5. ClusterIP Service

* Name: `nodejs-service`
* Type: ClusterIP
* Exposes all Deployment replicas on port 3000
* Internal port: 80

---

## 2️⃣ Deployment Steps

1. Ensure the Persistent Volume exists.
2. Apply ConfigMap and Secret:

```bash
kubectl apply -f configmap.yaml
kubectl apply -f secret.yaml
```

3. Apply PVC:

```bash
kubectl apply -f pvc.yaml
```

4. Apply Deployment:

```bash
kubectl apply -f deployment.yaml
```

5. Apply Service:

```bash
kubectl apply -f ClusterIP
```

6. Verify Pods:

```bash
kubectl get pods
kubectl describe pod <pod-name>
```
 <img width="930" height="201" alt="lab6-k5" src="https://github.com/user-attachments/assets/e5aa216e-34a3-40bf-a8d7-411900ac2775" />
<img width="815" height="199" alt="lab6-kf" src="https://github.com/user-attachments/assets/c743f4a8-c0d0-4ecf-9c6b-2d9be5108064" />


---

## 3️⃣ Notes

* If you get `ErrImagePull`:

  * Use the official Node.js image like `node:18-alpine`
  * Or build a local image and load it into Minikube
* ClusterIP service is **internal only** and cannot be accessed from outside the cluster directly.
* PVC ensures data persistence even if the pod is deleted.

---

## 4️⃣ Verification

* Check Pods:

```bash
kubectl get pods
```

* Check Service:

```bash
kubectl get svc
```

* Test Node.js server inside the cluster (example using Minikube):

```bash
kubectl port-forward svc/nodejs-service 8080:80
curl http://localhost:8080
```

* Expected output:

```
Hello from Node.js!
```

---

```

---


