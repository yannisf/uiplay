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
        booleanParam(name: 'DEPLOY_ON_AZURE', defaultValue: false, description: 'Deploy on Azure')
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


        stage('Package & Install') {
            steps {
                script {
                    def cmd = 'mvn install -Dmaven.test.skip'
                    if (params.BUILD_UI) {
                        cmd += ' -Pui'
                    } else {
                        println 'Building without UI!'
                    }
                    sh cmd
                }
            }
        }

        stage('Deploy on Azure') {
            when {
                allOf {
                    expression {
                        return params.RUN_TESTS
                    }
                    expression {
                        return params.DEPLOY_ON_AZURE
                    }
                }
            }
            steps {
                script {
                    def remote = [:]
                    remote.name = "FRLAB"
                    remote.host = "frlab.eu"
                    remote.allowAnyHosts = true
                    withCredentials([sshUserPrivateKey(credentialsId: 'e578d1d8-5891-4b37-8039-7bd1a80407c7', keyFileVariable: 'identity', passphraseVariable: 'passphrase', usernameVariable: 'username')]) {
                        remote.user = username
                        remote.passphrase = passphrase
                        remote.identityFile = identity
                        sshCommand remote: remote, command: "echo \"Starting upload: \$(date)\" >> jenkins.log"
                        sshPut remote: remote, from: 'application/target/uiplay.war', into: '/home/yannis/uiplay/app', failOnError: true
                        sshCommand remote: remote, command: "echo \"Finished upload: \$(date)\" >> jenkins.log"
                    }
                }
            }
        }

        stage('Javadoc') {
            steps {
                sh 'mvn javadoc:aggregate'
                echo 'Javadoc stub'
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
                        checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/target/checkstyle-result.xml', unHealthy: ''
                    }
                }
                stage('Findbugs') {
                    steps {
                        sh 'mvn findbugs:findbugs'
                        findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: '**/target/findbugsXml.xml', unHealthy: ''
                    }
                }
                stage('PMD') {
                    steps {
                        sh 'mvn pmd:pmd'
                        pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '**/target/pmd.xml', unHealthy: ''
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                if (params.RUN_TESTS) {
                    junit '**/target/surefire-reports/junitreports/**.xml'
                    step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
                }
            }
            archiveArtifacts artifacts: 'application/target/uiplay.war'
            step([$class: 'AnalysisPublisher'])
            step([$class: 'JavadocArchiver', javadocDir: 'target/site/apidocs'])
        }
    }
}