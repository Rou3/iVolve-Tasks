def call(image) {
    stage('BuildImage') {
        sh "docker build -t ${image} -f docker-labs/iVolve-Tasks/Jenkins/lab23/Dockerfile docker-labs/iVolve-Tasks/Jenkins/lab23"
    }
}
