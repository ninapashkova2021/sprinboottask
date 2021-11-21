node('builder') {
    String dockerComposeFileUrl = 'https://raw.githubusercontent.com/ninapashkova2021/sprinboottask/master/docker-compose.yml'

    stage('Fetch code') {
        checkout([$class: 'GitSCM',
        branches: [[name: '*/master']],
        extensions: [],
        userRemoteConfigs: [[url: 'https://github.com/ninapashkova2021/sprinboottask.git']]])
    }

    stage('Build') {
        sh 'chmod +x gradlew && ./gradlew build testClasses -x test --no-daemon --stacktrace'
    }

    stage('Run unit tests') {
        sh './gradlew test --no-daemon'
    }

    stage('Run integration tests') {
        sh './gradlew integrationTest --no-daemon'
    }

    stage('Deploy') {
        node('qa-node') {
            sh "sudo curl -o ${dockerComposeFileUrl} --output docker-compose.yaml"
            sh 'sudo docker-compose down'
            sh 'sudo docker-compose up'
        }
    }
}