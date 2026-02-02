**Jenkins CI/CD Pipeline with Docker & Kubernetes (Lab22)

This repository demonstrates a complete CI/CD pipeline using Jenkins that:
 • Builds a Java application using Maven
 • Creates a Docker image
 • Pushes the image to Docker Hub
 • Deploys the application to Kubernetes (Minikube)

⸻

🧱 Tech Stack
 • Jenkins
 • Maven
 • Docker
 • Docker Hub
 • Kubernetes (Minikube)
 • GitHub

⸻

📂 Project Structure

Jenkins_App/
│
├── Jenkinsfile
├── Dockerfile
├── pom.xml
├── src/
└── README.md


⸻

🚀 Jenkins Pipeline Stages

1️⃣ Clone Repository
Pulls the source code from GitHub.

2️⃣ Build Application
Uses Maven to compile and package the Java application:
mvn clean package

3️⃣ Build Docker Image
Builds a Docker image for the application:
docker build -t <dockerhub-user>/jenkins-app:latest .

4️⃣ Push Image to Docker Hub
Authenticates using Jenkins credentials and pushes the image.

<img width="929" height="110" alt="lab22-1" src="https://github.com/user-attachments/assets/e6cc6ad0-fb3b-4ec4-b41f-24fa30fc890c" />

5️⃣ Create Kubernetes Deployment YAML
Dynamically generates a deployment.yaml file.

<img width="815" height="70" alt="lab22-2" src="https://github.com/user-attachments/assets/0de6c741-1fdc-471f-b992-b835aad97e2f" />

6️⃣ Update Deployment Image
Replaces the placeholder image with the new Docker image tag.

7️⃣ Deploy to Kubernetes
 • Applies the deployment to Minikube
 • Exposes the service using NodePort
 
⸻

🔐 Jenkins Credentials Required

Credential ID Type Description
dockerhub-creds Username/Password Docker Hub login
kubeconfig-minikube Secret File Minikube kubeconfig file

<img width="1155" height="149" alt="lab22" src="https://github.com/user-attachments/assets/812830c6-d43a-4d4b-9085-44fcbd0cebb8" />

⸻

⚙️ Prerequisites
 • Jenkins installed with:
 • Docker
 • Maven
 • kubectl
 • Minikube running
 • Docker Hub account

⸻

📌 How to Run
 1. Start Minikube

minikube start


 2. Configure Jenkins credentials
 3. Create a Jenkins pipeline job and link it to this repo
 4. Run the pipeline 🎉

⸻

📷 Output

After successful deployment, Jenkins will print the service URL:

minikube service jenkins-app --url

<img width="814" height="220" alt="lab22-4-1" src="https://github.com/user-attachments/assets/b38de393-fb58-4d30-ae65-3a484d731d28" />

<img width="1066" height="235" alt="lab22-4" src="https://github.com/user-attachments/assets/060085d8-e535-46ff-8bfe-2709bed539e4" />


⸻

✨ Author

Rawan Osama
