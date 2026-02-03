def call(image) {
    stage('ScanImage') {
        sh "echo Scanning image ${image}"
    }
}

