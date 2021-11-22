node('builder') {
    String dockerComposeFileUrl = 'https://raw.githubusercontent.com/ninapashkova2021/sprinboottask/master/docker-compose.yml'
    String episodateAppVersion = "1.${currentBuild.number}"

    stage('Fetch code') {
        checkout([$class: 'GitSCM',
        branches: [[name: '*/episodate_jenkins_ci']],
        extensions: [],
        userRemoteConfigs: [[url: 'https://github.com/ninapashkova2021/sprinboottask.git']]])
    }

    stage('Build') {
        println "Current app version is ${episodateAppVersion}"
        sh "chmod +x gradlew"
        sh "./gradlew build testClasses -x test -PappVersion=${episodateAppVersion} --no-daemon --stacktrace"
    }

    stage('Run unit tests') {
        //sh './gradlew test --no-daemon'
    }

    stage('Run integration tests') {
        //sh './gradlew integrationTest --no-daemon'
    }

    stage('Push to Docker Hub') {
        String episodateImageTag = "ninapashkova/episodate_listener:${episodateAppVersion}"
        sh "sudo docker build -t ${episodateImageTag} --build-arg EPISODATE_APP_VERSION=${episodateAppVersion} ."
        withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials',
                                            passwordVariable: 'dockerHubPassword',
                                            usernameVariable: 'dockerHubUser')]) {
            sh "sudo docker login -u ${dockerHubUser} -p ${dockerHubPassword}"
        }
        sh "sudo docker push ${episodateImageTag}"
    }

    stage('Deploy') {
        node('qa-node') {
            sh "sudo curl ${dockerComposeFileUrl} --output docker-compose.yaml"
            sh 'sudo docker-compose down'
            sh "export EPISODATE_APP_VERSION=${episodateVersion}"
            sh 'sudo docker-compose up'
        }
    }
}