pipeline {
    agent any
    options {
        timestamps()
    }
    tools {
        jdk 'Java 10'
        maven 'Maven 3.5.4'
    }
    stages {
        stage('Initialize') {
            steps {
                sh 'java -version'
                sh 'mvn --version'
                nodejs('NodeJS 8.11.4') {
                    sh 'node --version'
                    sh 'npm --version'
                }
            }
        }
        stage('Build') {
            parallel {
                stage('server') {
                    stages {
                        stage('compile') {
                            steps {
                                sh 'mvn clean compile'
                            }
                        }
                        stage('test') {
                            steps {
                                sh 'mvn test'
                                jacoco()
                            }
                        }
                        stage('package') {
                            steps {
                                sh 'mvn package'
                            }
                        }
                    }
                }
                stage('client') {
                    steps {
                        dir('ng') {
                            nodejs('NodeJS 8.11.4') {
                                sh "npm install"
                                sh "npm run build"
                            }
                        }
                    }
                }
            }
        }
        stage('Code Analysis') {
            parallel {
                stage('Checkstyle') {
                    steps {
                        sh "mvn checkstyle:checkstyle"
                        checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/checkstyle-result.xml', unHealthy: ''
                    }
                }
                stage('Findbugs') {
                    steps {
                        sh "mvn findbugs:findbugs"
                        findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: 'target/findbugsXml.xml', unHealthy: ''
                    }
                }
                stage('PMD') {
                    steps {
                        sh "mvn pmd:pmd"
                        pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/pmd.xml', unHealthy: ''
                    }
                }
            }
        }

        stage('Publishing reports') {
            steps {
                step([$class: 'AnalysisPublisher'])
            }
        }

        stage('Archiving artifact') {
            steps {
                archiveArtifacts artifacts: '**/*.war'
            }
        }

    }
}
