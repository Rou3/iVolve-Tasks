# Lab 14: StatefulSet with Headless Service (MySQL)

## 🎯 Objective
Deploy a **MySQL StatefulSet** in Kubernetes with:
- Secret-based root password
- Persistent storage via PVC
- Toleration for a tainted node
- Headless service for stable network identity

---

## 🧰 Requirements
- Kubernetes cluster
- kubectl configured
- Access to node with taint `node=worker:NoSchedule`
- PVC storage available (1Gi)

---

## 🪜 Steps

### 1️⃣ Create MySQL Secret
```bash
kubectl create secret generic mysql-secret \
  --from-literal=MYSQL_ROOT_PASSWORD=rootpass456
````

Check:

```bash
kubectl get secret mysql-secret
```

---

### 2️⃣ Headless Service

File: `mysql-headless-svc.yaml`

```yaml
apiVersion: v1
kind: Service
metadata:
  name: mysql
spec:
  clusterIP: None
  selector:
    app: mysql
  ports:
    - port: 3306
      name: mysql
```

Apply:

```bash
kubectl apply -f mysql-headless-svc.yaml
```

---

### 3️⃣ StatefulSet with PVC, Secret, and Toleration

File: `mysql-statefulset.yaml`

```yaml
apiVersion: apps/v1
kind: StatefulSet
metadata:
  name: mysql
spec:
  serviceName: mysql
  replicas: 1
  selector:
    matchLabels:
      app: mysql
  template:
    metadata:
      labels:
        app: mysql
    spec:
      tolerations:
        - key: "node"
          operator: "Equal"
          value: "worker"
          effect: "NoSchedule"
      containers:
        - name: mysql
          image: mysql:5.7
          ports:
            - containerPort: 3306
          env:
            - name: MYSQL_ROOT_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: mysql-secret
                  key: MYSQL_ROOT_PASSWORD
          volumeMounts:
            - name: mysql-data
              mountPath: /var/lib/mysql
  volumeClaimTemplates:
    - metadata:
        name: mysql-data
      spec:
        accessModes:
          - ReadWriteOnce
        storageClassName: standard
        resources:
          requests:
            storage: 1Gi
```

Apply:

```bash
kubectl apply -f mysql-statefulset.yaml
```

---

### 4️⃣ Verify Resources

```bash
kubectl get pods
kubectl get statefulset
kubectl get pvc
kubectl get svc
```

Expected:

* Pod: `mysql-0` → Running
* StatefulSet: 1/1
* PVC: Bound
* Service: ClusterIP=None

---

### 5️⃣ Connect to MySQL

```bash
kubectl exec -it mysql-0 -- mysql -u root -p
```

Password: `rootpass456`

Expected prompt:

```
mysql>
```

