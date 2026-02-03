def call(String image) {
    echo "IMAGE NAME = ${image}"
    dir('Jenkins/lab23') {
        sh "docker build -t ${image} ."
    }
}
