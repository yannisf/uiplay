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
        booleanParam(name: 'BUILD_UI', defaultValue: false, description: 'Build UI')
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
                    when {
                        expression {
                            return params.BUILD_UI
                        }
                    }
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


        stage('Package') {
            parallel {
                stage('Application') {
                    steps {
                        script {
                            def cmd = 'mvn package -Dmaven.test.skip'
                            if (params.BUILD_UI == 'true') {
                                cmd += ' -Pui'
                            } else {
                                println 'Building without UI!'
                            }
                            sh cmd
                        }
                    }
                }
                stage('Javadoc') {
                    steps {
                        sh 'mvn javadoc:jar'
                    }
                }
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
            script {
                if (params.RUN_TESTS) {
                    junit 'target/surefire-reports/junitreports/**.xml'
                    step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
                }
            }
            archiveArtifacts artifacts: 'target/uiplay.war, target/uiplay-javadoc.jar'
            step([$class: 'AnalysisPublisher'])
            step([$class: 'JavadocArchiver', javadocDir: 'target/apidocs'])
        }
    }

}