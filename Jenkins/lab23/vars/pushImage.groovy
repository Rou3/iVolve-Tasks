def call(String image) {
    withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', 
                                     usernameVariable: 'DOCKER_USER', 
                                     passwordVariable: 'DOCKER_PASS')]) {
        sh "docker login -u $DOCKER_USER -p $DOCKER_PASS"
        sh "docker push ${image}"
    }
}
