pipeline {
    agent any

    environment {
        KUBECONFIG = '/var/lib/jenkins/.kube/config'
    }

    stages {

        stage('Build Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker') {
            steps {
                sh 'docker build -t demo-app:${BUILD_NUMBER} .'
            }
        }

        stage('Load Image In Minikube') {
            steps {
                sh 'minikube image load demo-app:${BUILD_NUMBER}'
            }
        }

        stage('Update Deployment') {
            steps {
                sh '''
                kubectl set image deployment/demo-app \
                demo-app=demo-app:${BUILD_NUMBER}
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