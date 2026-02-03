def call(image) {
    stage('PushImage') {
        sh "docker push ${image}"
    }
}

