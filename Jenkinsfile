pipeline {
    agent any
    environment {
        KUBECONFIG = '/var/lib/jenkins/.kube/config'
        IMAGE = "docker.io/library/demo-app:${BUILD_NUMBER}"
    }
    stages {
        stage('Build Maven') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build Docker') {
            steps {
                sh 'docker build -t ${IMAGE} .'
            }
        }
        stage('Load Image In Minikube') {
            steps {
                sh '''
                set -e
                minikube image load ${IMAGE}
                echo "Verifica immagine caricata:"
                minikube image ls | grep ${IMAGE} || (echo "IMMAGINE NON CARICATA" && exit 1)
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
