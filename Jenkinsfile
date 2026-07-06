pipeline {
    agent any
    environment {
        KUBECONFIG = '/var/lib/jenkins/.kube/config'
        IMAGE = "demo-app:${BUILD_NUMBER}"
    }
    stages {
        stage('Build Maven') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Docker in Minikube') {
            steps {
                sh '''
                eval $(minikube docker-env)
                docker build -t ${IMAGE} .
                docker images | grep demo-app
                '''
            }
        }
        stage('Update Deployment') {
            steps {
                sh '''
                kubectl set image deployment/demo-app \
                demo-app=${IMAGE}
                '''
            }
        }
        stage('Wait Rollout') {
            steps {
                sh 'kubectl rollout status deployment/demo-app'
            }
        }
    }
}
