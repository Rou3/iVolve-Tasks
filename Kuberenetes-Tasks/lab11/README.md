# Lab 20: Securing Kubernetes with RBAC and Service Accounts

## Objective
- Create a Service Account for Jenkins in the `ivolve` namespace.
- Create a secret and retrieve the Service Account token.
- Define a Role with read-only access to Pods (`get`, `list`) in the `ivolve` namespace.
- Bind the Role to the Service Account using a RoleBinding.
- Validate that the Service Account can only list pods and cannot perform other actions.

---

## Steps

### 1. Create the `ivolve` namespace (if not already created)
```bash
kubectl create namespace ivolve
kubectl get ns
````


### 2. Create Service Account

Create a YAML file `jenkins-sa.yaml`:

```yaml
apiVersion: v1
kind: ServiceAccount
metadata:
  name: jenkins-sa
  namespace: ivolve
```

Apply it:

```bash
kubectl apply -f jenkins-sa.yaml
kubectl get sa -n ivolve
```
<img width="1074" height="70" alt="lab11-k1" src="https://github.com/user-attachments/assets/977c5c83-0198-4cb0-8f75-c589fbefac1a" />
<img width="1077" height="90" alt="lab11-k2" src="https://github.com/user-attachments/assets/c338dd66-cc5a-41f0-98c2-bf565e4879f8" />

---

### 3. Create Secret for the Service Account token

Create a YAML file `jenkins-sa-secret.yaml`:

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: jenkins-sa-token
  namespace: ivolve
  annotations:
    kubernetes.io/service-account.name: jenkins-sa
type: kubernetes.io/service-account-token
```

Apply it:

```bash
kubectl apply -f jenkins-sa-secret.yaml
```
<img width="1075" height="66" alt="lab11-k3" src="https://github.com/user-attachments/assets/bb3f3448-d391-4214-9274-268455066963" />

Retrieve the token:

```bash
kubectl get secret jenkins-sa-token -n ivolve -o jsonpath='{.data.token}' | base64 --decode
```
<img width="1075" height="245" alt="lab11-k4" src="https://github.com/user-attachments/assets/98670751-3100-4b86-86d4-647c2b119831" />

---

### 4. Create Role with read-only access to Pods

Create a YAML file `reader-role.yaml`:

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: Role
metadata:
  name: pod-reader
  namespace: ivolve
rules:
  - apiGroups: [""]
    resources: ["pods"]
    verbs: ["get", "list"]
```

Apply it:

```bash
kubectl apply -f reader-role.yaml
kubectl get role -n ivolve
```
<img width="1077" height="76" alt="lab11-k5" src="https://github.com/user-attachments/assets/94f61774-53e7-47ef-917f-c39115176b8e" />

---

### 5. Create RoleBinding to bind the Role to the Service Account

Create a YAML file `pod-reader-binding-sa.yaml`:

```yaml
apiVersion: rbac.authorization.k8s.io/v1
kind: RoleBinding
metadata:
  name: pod-reader-binding
  namespace: ivolve
subjects:
  - kind: ServiceAccount
    name: jenkins-sa
    namespace: ivolve
roleRef:
  kind: Role
  name: pod-reader
  apiGroup: rbac.authorization.k8s.io
```

Apply it:

```bash
kubectl apply -f pod-reader-binding-sa.yaml
kubectl get rolebinding -n ivolve
```
<img width="1076" height="72" alt="lab11-k6" src="https://github.com/user-attachments/assets/f84ae1ea-5bff-4b5a-a4c6-d8cbb395af6a" />

---

### 6. Validate the Service Account permissions

#### Can list pods:

```bash
kubectl auth can-i list pods \
  --as=system:serviceaccount:ivolve:jenkins-sa \
  -n ivolve
```

Expected output: `yes`

#### Cannot create or delete pods:

```bash
kubectl auth can-i create pods \
  --as=system:serviceaccount:ivolve:jenkins-sa \
  -n ivolve
```

Expected output: `no`
<img width="1075" height="198" alt="lab11-kf" src="https://github.com/user-attachments/assets/bdc3598b-1f07-4382-871f-0720ae0b24ff" />

---

## Notes

* Using `--as=system:serviceaccount:<namespace>:<sa-name>` allows testing RBAC permissions as a specific Service Account.
* The Role grants **read-only access** to Pods only in the `ivolve` namespace.
* The RoleBinding connects the Role to the Service Account; without it, the SA has no permissions.

---

## Validation Checklist

* [x] Namespace `ivolve` exists
* [x] Service Account `jenkins-sa` created
* [x] Secret token generated and retrievable
* [x] Role `pod-reader` created with `get` and `list` on Pods
* [x] RoleBinding binds Role to Service Account
* [x] Service Account can list pods but cannot modify them

تحبي أعملهولك؟
```
