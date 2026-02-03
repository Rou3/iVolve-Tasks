def call() {
    dir('Jenkins/lab23') {
        sh 'mvn clean package -DskipTests'
        sh 'ls -l target || true'
    }
}

