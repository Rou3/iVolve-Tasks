# Lab 19: Node-Wide Pod Management with DaemonSet

## Objective
- Create a namespace for monitoring.
- Deploy a DaemonSet for Prometheus Node Exporter that tolerates all taints.
- Verify that a Node Exporter pod is scheduled on each node.
- Confirm that metrics are exposed correctly on port `9100`.

---

## Steps

### 1. Create monitoring namespace
```bash
kubectl create namespace monitoring
kubectl get ns
````
<img width="1077" height="225" alt="Screenshot 2026-01-27 181907" src="https://github.com/user-attachments/assets/daa0cd65-f98f-4c0b-be58-8a6960580120" />


### 2. Deploy Prometheus Node Exporter DaemonSet

Create a YAML file `node-exporter-daemonset.yaml`:

```yaml
apiVersion: apps/v1
kind: DaemonSet
metadata:
  name: node-exporter
  namespace: monitoring
  labels:
    app: node-exporter
spec:
  selector:
    matchLabels:
      app: node-exporter
  template:
    metadata:
      labels:
        app: node-exporter
    spec:
      tolerations:
        - operator: "Exists"   # Tolerate all node taints
      containers:
        - name: node-exporter
          image: prom/node-exporter:v1.7.0
          ports:
            - containerPort: 9100
              name: metrics
```

Apply the DaemonSet:

```bash
kubectl apply -f node-exporter-daemonset.yaml
```
<img width="1024" height="111" alt="lab10-first" src="https://github.com/user-attachments/assets/49db9cca-de2f-4c9d-ae6f-ea933b2c1358" />

---

### 3. Verify Node Exporter pods on all nodes

```bash
kubectl get pods -n monitoring -o wide
kubectl get daemonset -n monitoring
```

* Each node should have **one pod** running.
* `DESIRED` pods = number of nodes
* `READY` pods should eventually match `DESIRED`.

---

### 4. Confirm metrics exposure

#### Option 1: Using port-forward

```bash
kubectl port-forward -n monitoring pod/<node-exporter-pod-name> 9100:9100
```
<img width="1029" height="372" alt="lab10-kf" src="https://github.com/user-attachments/assets/a2c6f648-c400-422b-a614-2412936a09c0" />

Then open in browser:

```
http://localhost:9100/metrics
```
<img width="1075" height="416" alt="lab10-browser" src="https://github.com/user-attachments/assets/73ebb55c-1435-4b8b-bf8a-8f5e13e172a9" />

---

## Notes

* Node Exporter is written in Go, so you may see Go runtime metrics like:

  * `go_goroutines`
  * `go_memstats_*`
* These are normal and in addition to node metrics like CPU, memory, network, and disk.
* On Minikube, it may take a few seconds for all pods to reach `Running` status as the image downloads.

---

## Validation Checklist

* [x] Namespace `monitoring` created
* [x] DaemonSet deployed and tolerates all taints
* [x] One Node Exporter pod scheduled per node
* [x] Metrics exposed correctly on port `9100`
* [x] Metrics include CPU, memory, disk, and network stats

```

