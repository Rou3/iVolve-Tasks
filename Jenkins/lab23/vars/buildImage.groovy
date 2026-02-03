def call(image) {
    stage('BuildImage') {
        dir('docker-labs/iVolve-Tasks/Jenkins/lab23') {
            sh "docker build -t ${image} ."
        }
    }
}
