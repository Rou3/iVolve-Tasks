# Lab 13: Persistent Storage Setup for Application Logging

## 🎯 Objective
Configure **Persistent Storage** in Kubernetes for application logging using:
- Persistent Volume (PV)
- Persistent Volume Claim (PVC)

The setup uses **static provisioning** with `hostPath` storage.

---

## 🧰 Requirements
- Running Kubernetes cluster
- kubectl configured
- Access to the application node (or Minikube)

---

## 🪜 Steps

### 1️⃣ Prepare Node Storage
Create the directory on the node filesystem:
```bash
sudo mkdir -p /mnt/app-logs
sudo chmod 777 /mnt/app-logs
````

---

### 2️⃣ Create Persistent Volume (PV)

File: `pv.yaml`

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: app-logs-pv
spec:
  capacity:
    storage: 1Gi
  accessModes:
    - ReadWriteMany
  persistentVolumeReclaimPolicy: Retain
  hostPath:
    path: /mnt/app-logs
```

Apply:

```bash
kubectl apply -f pv.yaml
```

---

### 3️⃣ Create Persistent Volume Claim (PVC)

File: `pvc.yaml`

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: app-logs-pvc
spec:
  accessModes:
    - ReadWriteMany
  storageClassName: ""
  volumeName: app-logs-pv
  resources:
    requests:
      storage: 1Gi
```

Apply:

```bash
kubectl apply -f pvc.yaml
```

---

### 4️⃣ Verify

```bash
kubectl get pv
kubectl get pvc
```

Expected:

* PV status: `Bound`
* PVC status: `Bound`
* Access Mode: `RWX`
* Reclaim Policy: `Retain`

