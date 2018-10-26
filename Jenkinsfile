pipeline {

    agent any
    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    tools {
        jdk 'Java 10'
        maven 'Maven 3.5.4'
    }
    environment {
        MY_VAR = "xx"
    }
    parameters {
        booleanParam(name: 'RUN_TESTS', defaultValue: false, description: 'Run tests')
        booleanParam(name: 'RUN_CODE_ANALYSIS', defaultValue: false, description: 'Run code analysis')
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
                git 'https://github.com/yannisf/uiplay.git'
                echo "Checked out ${GIT_BRANCH} (${GIT_COMMIT})"
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
                            when {
                                expression {
                                    return params.RUN_TESTS
                                }
                            }
                            steps {
                                sh 'mvn test -Pcoverage'
                                jacoco()
                            }
                        }
                    }
                }
                stage('client') {
                    steps {
                        dir('ng') {
                            nodejs('NodeJS 8.11.4') {
                                sh 'npm install'
                                sh 'npm run build'
                                sh 'mvn'
                            }
                        }
                    }
                }
            }
        }

        stage('Javadoc') {
            steps {
                sh 'mvn javadoc:jar'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -Dmaven.test.skip'
            }
        }

        stage('Code Analysis') {
            when {
                expression {
                    return params.RUN_CODE_ANALYSIS
                }
            }
            parallel {
                stage('Checkstyle') {
                    steps {
                        sh 'mvn checkstyle:checkstyle'
                        checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/checkstyle-result.xml', unHealthy: ''
                    }
                }
                stage('Findbugs') {
                    steps {
                        sh 'mvn findbugs:findbugs'
                        findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: 'target/findbugsXml.xml', unHealthy: ''
                    }
                }
                stage('PMD') {
                    steps {
                        sh 'mvn pmd:pmd'
                        pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/pmd.xml', unHealthy: ''
                    }
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/uiplay.war', 'target/uiplay-javadoc.jar'
            archiveArtifacts artifacts: 'target/uiplay.war'
            step([$class: 'AnalysisPublisher'])
            step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
            step([$class: 'JavadocArchiver', javadocDir: 'target/site/apidocs'])
        }
    }

}