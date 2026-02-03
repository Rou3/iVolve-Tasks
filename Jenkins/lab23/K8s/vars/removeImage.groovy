def call(image) {
    stage('RemoveImageLocally') {
        sh "docker rmi ${image} || true"
    }
}

