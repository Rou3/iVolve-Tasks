stage('BuildImage') {
    steps {
        script {
            buildImage("rabdelrahman3332/jenkins-app:latest")
        }
    }
}

