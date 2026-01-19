# Lab 11: Namespace Management and Resource Quota Enforcement

## Objective
- Create a Kubernetes namespace called `ivolve`.
- Apply a ResourceQuota to limit the number of Pods to **2** within this namespace.

---

## Steps

### 1️⃣ Create Namespace
Run the following command to create the namespace:

```bash
kubectl create namespace ivolve
````

Verify the namespace:

```bash
kubectl get namespaces
```

---

### 2️⃣ Apply Resource Quota

Create a YAML file called `quota.yaml` with the following content:

```yaml
apiVersion: v1
kind: ResourceQuota
metadata:
  name: pod-quota
  namespace: ivolve
spec:
  hard:
    pods: "2"
```

Apply the quota:

```bash
kubectl apply -f quota.yaml
```

Check the quota status:

```bash
kubectl get resourcequota -n ivolve
kubectl describe resourcequota pod-quota -n ivolve
```

---

### 3️⃣ Test the Quota

Try to create Pods within the namespace:

```bash
kubectl run test1 --image=nginx -n ivolve
kubectl run test2 --image=nginx -n ivolve
kubectl run test3 --image=nginx -n ivolve
```

* The third Pod will fail because the quota limits the number of Pods to 2:

```
Error from server (Forbidden): exceeded quota: pod-quota, requested: pods=1, used: pods=2, limited: pods=2
```

