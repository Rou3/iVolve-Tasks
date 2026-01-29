# Lab 22 - Jenkins Pipeline for Application Deployment

## Overview
This lab demonstrates how to automate the complete deployment workflow of an application from GitHub to a Kubernetes cluster using **Jenkins CI/CD**.  
The pipeline will clone the source code, run unit tests, build the app, build and push Docker images, update the deployment manifest, and deploy to Kubernetes.

---

## Prerequisites
- **Jenkins** (on VM, Docker, or Kubernetes)
- **Docker** installed and running
- **Kubernetes cluster** with namespace `ivolve`
- **Docker Hub account**
- **kubectl** configured to access the cluster
- At least 5 pod quota in `ivolve` namespace

---

## Project Structure
```

task-22/
├── Jenkinsfile                
├── deployment.yaml            
├── jenkins-deployment.yaml    
├── docker-compose-jenkins.yml 
└── README.md

````

---

## Pipeline Workflow
The Jenkins pipeline performs the following steps:

1. **Checkout Source Code**
   - Clone repository: `https://github.com/Ibrahim-Adel15/Jenkins_App.git`
2. **Run Unit Tests**
   - Node.js: `npm test`
   - Java: `mvn test`
   - Python: `pytest`
3. **Build Application**
   - Node.js: `npm install`
   - Java: `mvn clean package`
   - Python: `pip install -r requirements.txt`
4. **Build Docker Image**
   - Uses Dockerfile from GitHub
   - Tags image with build number and `latest`
5. **Push Image to Docker Hub**
   - Authenticates with Jenkins-stored credentials
6. **Delete Local Image**
   - Clean up local Docker images
7. **Update `deployment.yaml`**
   - Replace image with the new Docker tag
8. **Deploy to Kubernetes**
   - Apply deployment to `ivolve` namespace
   - Wait for rollout completion

**Post Actions:**
- `always`: Cleanup workspace
- `success`: Log success message
- `failure`: Log failure message

---

## Setup Jenkins

### Plugins Required
- Pipeline (workflow-aggregator)
- Pipeline: Declarative
- Git
- Docker Pipeline
- Kubernetes CLI
- Credentials

### Credentials in Jenkins
1. **Docker Hub**
   - Username & Password
2. **kubeconfig**
   - File credential for cluster access

### Recommended Pipeline Configuration
- Use **Pipeline script from SCM**
- Script path: `Jenkinsfile` in the repository

---

## Verify Deployment
```bash
kubectl get deployment jenkins-app -n ivolve
kubectl get pods -n ivolve -l app=jenkins-app
kubectl get svc -n ivolve
kubectl logs -n ivolve -l app=jenkins-app --tail=50
````

---

## Notes

* Jenkins can run in VM, Docker, or Kubernetes.
* Pipeline automatically updates the Docker image in `deployment.yaml`.
* Ensure Jenkins has access to Docker (socket & CLI) and kubectl.
* Use declarative pipelines for better maintainability.

---

## References

* [Jenkins Documentation](https://www.jenkins.io/doc/)
* [Kubernetes Deployments](https://kubernetes.io/docs/concepts/workloads/controllers/deployment/)
* [Docker Hub](https://hub.docker.com/)


