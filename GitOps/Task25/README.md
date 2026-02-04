# Lab 25 – Full GitOps Workflow with Jenkins & ArgoCD

## **1️⃣ Clone the Project**

```bash
git clone https://github.com/Ibrahim-Adel15/Jenkins_App.git
cd Jenkins_App
```

> All project files are here:
>
> * Dockerfile
> * Application files
> * You will create a `k8s/` folder for the deployment YAMLs.

---

## **2️⃣ Prepare k8s Folder & Deployment**

### 2.1 Create `k8s` folder (if not existing)

```bash
mkdir k8s
```

### 2.2 Create `deployment.yaml`

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: jenkins-app
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jenkins-app
  template:
    metadata:
      labels:
        app: jenkins-app
    spec:
      containers:
      - name: app
        image: rabdelrahman3332/jenkins-app:latest
        ports:
        - containerPort: 8080
```

> Later, the Jenkins pipeline will automatically update the Docker image here.

---

## **3️⃣ Jenkins Pipeline**

### 3.1 Create `Jenkinsfile` in the project

```groovy
pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'rabdelrahman3332'
        IMAGE_NAME = 'jenkins-app'
        IMAGE_TAG = "${BUILD_NUMBER}"
        IMAGE = "${DOCKERHUB_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
    }

    stages {

        stage('Build App') {
            steps {
                echo "Building application..."
                // Add build commands if needed
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE .'
            }
        }

        stage('Push Docker Image') {
            steps {
                sh 'docker push $IMAGE'
            }
        }

        stage('Delete Local Image') {
            steps {
                sh 'docker rmi $IMAGE'
            }
        }

        stage('Update deployment.yaml') {
            steps {
                sh """
                sed -i 's|image:.*|image: $IMAGE|' k8s/deployment.yaml
                """
            }
        }

        stage('Push Changes to GitHub') {
            steps {
                sh """
                git config user.email "jenkins@lab.com"
                git config user.name "jenkins"
                git add k8s/deployment.yaml
                git commit -m "update image to $IMAGE_TAG"
                git push origin main
                """
            }
        }
    }
}
```

---

## **4️⃣ Push Initial k8s Folder to GitHub**

```bash
git add k8s/deployment.yaml Jenkinsfile
git commit -m "Add k8s deployment and Jenkinsfile for GitOps"
git push origin main
```

---

## **5️⃣ Install ArgoCD on Kubernetes**

```bash
kubectl create namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
kubectl get pods -n argocd
```

### 5.1 Port-forward ArgoCD UI

```bash
kubectl port-forward svc/argocd-server -n argocd 8080:443
```

### 5.2 Login

```bash
kubectl get secret argocd-initial-admin-secret -n argocd -o jsonpath="{.data.password}" | base64 -d
```

* Username: `admin`
* Password: from the command above

---

## **6️⃣ Create ArgoCD Application**

1. Open ArgoCD UI: `https://localhost:8080`
2. Create **New Application**:

   * Name: `jenkins-app`
   * Project: `default`
   * Repo URL: `https://github.com/Ibrahim-Adel15/Jenkins_App.git`
   * Path: `k8s`
   * Cluster: `https://kubernetes.default.svc`
   * Namespace: `default`
   * Sync Policy: Automatic ✅

> Any update in `deployment.yaml` via Jenkins → ArgoCD will automatically deploy it to the cluster.

---

## **7️⃣ Test the Workflow**

1. Run the Jenkins pipeline
2. It will build the app and Docker image
3. Push the image to Docker Hub
4. Delete local Docker image
5. Update `deployment.yaml` with the new image
6. Push changes to GitHub
7. ArgoCD detects changes and deploys the updated app

---

## **8️⃣ Verify Deployment**

```bash
kubectl get pods
kubectl get svc
```

* Check pods are running with the new image
* Future updates pushed to GitHub → ArgoCD will deploy automatically

---

## **Notes**

* To update with a new Docker image in the future, either edit `deployment.yaml` or let Jenkins pipeline handle it
* Automatic sync ensures any GitHub push updates the cluster
* Add Service or Ingress if you want external access to the app

