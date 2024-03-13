pipeline {
    agent any
    environment {
        // Define the Docker image name using your Docker Hub username and the image name
        DOCKER_IMAGE = 'beyuneekschool/my-webapp'
        // Define the tag you want to use for your Docker image
        DOCKER_TAG = 'latest'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm: [$class: 'GitSCM', branches: [[name: '*/main']], userRemoteConfigs: [[url: 'https://github.com/beyuneek/Comp-367-Lab-3.git']]]
            }
        }
        stage('Build Maven Project') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Code Coverage') {
            steps {
                sh 'mvn jacoco:report'
                // Consider archiving the reports or publishing them to a code quality dashboard
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }
        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-beyuneekschool', usernameVariable: 'DOCKER_HUB_USER', passwordVariable: 'DOCKER_HUB_PASS')]) {
                    sh 'echo "$DOCKER_HUB_PASS" | docker login --username $DOCKER_HUB_USER --password-stdin'
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-beyuneekschool') {
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
                    }
                }
            }
        }
    }
    post {
        always {
            sh 'docker rmi -f $(docker images -q ${env.DOCKER_IMAGE}:${env.DOCKER_TAG}) || true'
        }
    }
}
