def call(image) {
    stage('BuildImage') {
        sh "docker build -t ${image} -f docker/Dockerfile ."
    }
}

