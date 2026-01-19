#Lab 3: Managing Configuration and Sensitive Data with ConfigMaps and Secrets

## Objective
- Store non-sensitive MySQL configuration variables using a **ConfigMap**.
- Store sensitive MySQL credentials securely using a **Secret**.
- Use **base64 encoding** for the Secret values.

---

## Steps

### 1️⃣ Create ConfigMap for non-sensitive data

Create a YAML file called `mysql-configmap.yaml`:

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: mysql-config
  namespace: ivolve
data:
  DB_HOST: mysql-service
  DB_USER: ivolve_user
````

Apply the ConfigMap:

```bash
kubectl apply -f mysql-configmap.yaml
```

Verify:

```bash
kubectl get configmap -n ivolve
kubectl describe configmap mysql-config -n ivolve
```
<img width="817" height="221" alt="lab3-k1" src="https://github.com/user-attachments/assets/5f0ecb51-9fff-43b7-8a5d-e4de70360e76" />

---

### 2️⃣ Create Secret for sensitive data

First, encode the sensitive values in **base64**:

```bash
echo -n 'my_password' | base64
echo -n 'root_password' | base64
```

Use the encoded values in a YAML file called `mysql-secret.yaml`:

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: mysql-secret
  namespace: ivolve
type: Opaque
data:
  DB_PASSWORD: <base64-encoded-password>
  MYSQL_ROOT_PASSWORD: <base64-encoded-root-password>
```

Apply the Secret:

```bash
kubectl apply -f mysql-secret.yaml
```

Verify:

```bash
kubectl get secret -n ivolve
kubectl describe secret mysql-secret -n ivolve
```
<img width="803" height="182" alt="lab3-k3" src="https://github.com/user-attachments/assets/fe7734a5-73d2-497a-b5e9-edcfe5fd8d7b" />

---

### 3️⃣ Using ConfigMap and Secret in Pods

Example snippet in a Pod or Deployment YAML:

```yaml
env:
  - name: DB_HOST
    valueFrom:
      configMapKeyRef:
        name: mysql-config
        key: DB_HOST
  - name: DB_USER
    valueFrom:
      configMapKeyRef:
        name: mysql-config
        key: DB_USER
  - name: DB_PASSWORD
    valueFrom:
      secretKeyRef:
        name: mysql-secret
        key: DB_PASSWORD
```
<img width="801" height="462" alt="lab3-k41" src="https://github.com/user-attachments/assets/ec3e829e-afd3-4c78-85d1-24743e249cc1" />

---

### ✅ Summary

* ConfigMaps are used for non-sensitive configuration data.
* Secrets store sensitive information securely with base64 encoding.
* Applications can reference ConfigMaps and Secrets as environment variables.

```


