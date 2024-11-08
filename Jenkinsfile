pipeline {
    agent any  // Utiliser n'importe quel agent disponible
    environment {
        SONARQUBE_SERVER = 'sq'  // Nom du serveur configuré dans Jenkins
        SONARQUBE_LOGIN = credentials('sonar-token')  // Nom des credentials configurés
        IMAGE_NAME = 'kaddem'  // Ajout des guillemets simples
        USER = 'hassen98'  // Ajout des guillemets simples
        EMAIL_RECIPIENTS = 'hassen.mrakbenjebahi@esprit.tn'
    }
    stages {
        stage('Checkout') {  // Première étape : récupération du code
            steps {
                git branch: 'master',
                url: 'https://github.com/hassenmrakbenjebahi/kaddem.git'
            }
        }



         stage("Compile") {
                    steps {
                        sh "mvn package"

                    }
                }

        stage("Unit Test") {
            steps {
                sh "mvn test"
            }
        }

        stage('SonarQube Analysis') {  // Analyse de qualité de code avec SonarQube
            steps {
                withSonarQubeEnv(SONARQUBE_SERVER) {  // Utilisation de l'environnement SonarQube
                    sh "mvn sonar:sonar -Dsonar.login=${SONARQUBE_LOGIN}"  // Lancer l'analyse avec Maven
                }
            }
        }

        stage('NEXUS') {
            steps {
                script {
                    echo "Deploying to Nexus..."
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: '192.168.50.4:8081',
                        groupId: 'tn.esprit.spring',
                        artifactId: 'kaddem',
                        version: "1.0.${env.BUILD_NUMBER}",
                        repository: 'maven-releases',
                        credentialsId: 'ski-deploy',
                        artifacts: [
                            [
                                artifactId: 'kaddem',
                                classifier: '',
                                file: '/var/lib/jenkins/workspace/kaddem@2/target/kaddem-1.0.0.jar',
                                type: 'jar'
                            ]
                        ]
                    )
                    echo "Deployment to Nexus completed!"
                }
            }
        }

        stage('Build Docker Image') {  // Déplacer en dehors de 'NEXUS'
            steps {
                script {
                    // Créer l'image Docker avec le fichier Dockerfile
                    sh "docker build -t ${USER}/${IMAGE_NAME}:1.0.${env.BUILD_NUMBER} ."
                }
            }
        }


        stage('Docker Push') {  // Déplacer en dehors de 'NEXUS'
              steps {
                  script {
                      withDockerRegistry(credentialsId: 'dockerhub') {
                        sh "docker push ${USER}/${IMAGE_NAME}:1.0.${env.BUILD_NUMBER}"
                      }
                  }
              }
          }
        stage('Docker Compose') {  // Nouveau stage pour Docker Compose
              steps {
                   script {
                       // Lancer les services définis dans docker-compose.yml
                       sh 'docker-compose up -d'  // Démarre les services en arrière-plan
                   }
              }
        }
    }

    post {
        success {
            echo 'Le pipeline a réussi.'
            emailext(
                subject: "Pipeline Success: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: "The pipeline has succeeded.\n\nDetails:\nJob: ${env.JOB_NAME}\nBuild: ${env.BUILD_NUMBER}\nStatus: SUCCESS",
                to: "${EMAIL_RECIPIENTS}"
            )
        }
        failure {
            echo 'Le pipeline a échoué.'
            emailext(
                subject: "Pipeline Failed: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: "The pipeline has failed.\n\nDetails:\nJob: ${env.JOB_NAME}\nBuild: ${env.BUILD_NUMBER}\nStatus: FAILURE\nPlease check the logs for more details.",
                to: "${EMAIL_RECIPIENTS}"
            )
        }
        always {
            echo 'Le pipeline est terminé.'
        }
    }
}