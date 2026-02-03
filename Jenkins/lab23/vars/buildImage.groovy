def call(image) {
    stage('BuildImage') {
        sh "docker build -t myimage:latest -f Dockerfile ."
    }
}

