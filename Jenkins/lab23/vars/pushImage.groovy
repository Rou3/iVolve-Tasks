def call(String image) {
    sh "docker push ${image}"
}

