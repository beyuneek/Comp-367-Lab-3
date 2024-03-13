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
                // Change to match your repository. Make sure Jenkins has access to clone it.
                git 'https://github.com/beyuneek/Comp-367-Lab-3.git'
            }
        }
        stage('Build Maven Project') {
            steps {
                // Standard Maven build step. Assumes you have Maven set up in Jenkins.
                sh 'mvn clean package'
            }
        }
        stage('Code Coverage') {
            steps {
                // Runs the Jacoco plugin for code coverage. Customize as needed.
                sh 'mvn jacoco:report'
                // You may want to archive the reports or publish them to a code quality dashboard here.
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Builds the Docker image with the tag specified in the environment variable.
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }
        stage('Docker Login') {
            steps {
                // Logs into Docker Hub using the credentials you added in Jenkins.
                withCredentials([usernamePassword(credentialsId: 'docker-hub-beyuneekschool', usernameVariable: 'DOCKER_HUB_USER', passwordVariable: 'DOCKER_HUB_PASS')]) {
                    sh 'echo "$DOCKER_HUB_PASS" | docker login --username $DOCKER_HUB_USER --password-stdin'
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    // Pushes the Docker image to Docker Hub using the credentials.
                    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-beyuneekschool') {
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
                    }
                }
            }
        }
    }
    post {
        always {
            // Clean up Docker images to prevent the build server's disk space from filling up.
            sh 'docker rmi -f $(docker images -q ${env.DOCKER_IMAGE}:${env.DOCKER_TAG}) || true'
        }
    }
}
