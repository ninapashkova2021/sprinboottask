node('qa-node2') {
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
            echo "It's deploy stage"
            echo "It's deploy stage2"
        }
    }
}