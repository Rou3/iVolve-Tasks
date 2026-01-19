# Lab 1: Node Isolation Using Taints in Kubernetes

## Objective
Demonstrate how to isolate a Kubernetes node using **taints** to prevent pods from being scheduled on it unless they explicitly tolerate the taint.

---

## Prerequisites
- Minikube installed
- kubectl configured
- System capable of running a multi-node Minikube cluster

---

## Steps

### 1. Start a Kubernetes Cluster with 2 Nodes
```bash
minikube delete
minikube start --nodes=2
````

### 2. Verify Nodes

```bash
kubectl get nodes
```

Expected output:

```
minikube
minikube-m02
```

---

### 3. Apply Taint to One Node

Taint the worker node with key-value `node=worker` and effect `NoSchedule`:

```bash
kubectl taint nodes minikube-m02 node=worker:NoSchedule
```

---

### 4. Verify the Taint

Describe all nodes to confirm the taint:

```bash
kubectl describe nodes
```

You should see the following under **Taints** for the tainted node:

```
node=worker:NoSchedule
```
<img width="866" height="128" alt="lab1-k1" src="https://github.com/user-attachments/assets/c29772aa-df3e-49ec-b942-d1ea95706cd0" />
<img width="816" height="224" alt="lab1-k11" src="https://github.com/user-attachments/assets/55d733f7-ad1c-4e9e-a97a-fbc591ce73a9" />

---
