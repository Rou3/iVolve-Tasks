def call() {
    stage('DeployOnK8s') {
        sh "kubectl apply -f k8s/"
    }
}

