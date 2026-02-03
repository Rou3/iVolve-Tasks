def call(String image) {
    dir('Jenkins/lab23') {
        sh "docker build -t ${image} ."
    }
}

