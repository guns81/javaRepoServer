pipeline {
    agent any

    environment {
        KUBECONFIG = '/var/lib/jenkins/.kube/config'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker') {
            steps {
                sh 'docker build -t demo-app:latest .'
            }
        }

        stage('Deploy Kubernetes') {
            steps {
                sh 'kubectl apply -f deployment.yaml'
                sh 'kubectl apply -f service.yaml'
            }
        }

        stage('Rollout Restart') {
            steps {
                sh 'kubectl rollout restart deployment/demo-app'
                sh 'kubectl rollout status deployment/demo-app'
            }
        }

        stage('Verify') {
            steps {
                sh 'kubectl get pods'
                sh 'kubectl get deployments'
                sh 'kubectl get svc'
            }
        }
    }
}
