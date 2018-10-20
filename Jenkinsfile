node {

    def mvnHome

    stage('Preparation') { // for display purposes
        git 'https://github.com/yannisf/uiplay.git'
        mvnHome = tool 'Maven 3.5.4'
    }

    parallel 'Backend': {

        stage('Backend/build') {
            sh "'${mvnHome}/bin/mvn' clean compile"
        }

        stage('Backend/test') {
            sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore test"
            step([$class: 'Publisher', reportFilenamePattern: '**/testng-results.xml'])
            step([$class: 'JacocoPublisher'])
        }

        stage('Backend/package') {
            sh "'${mvnHome}/bin/mvn' package -Dmaven.main.skip -Dmaven.test.skip"
        }

    }, 'Frontend': {
        dir('ng') {
            nodejs('NodeJS 8.11.4') {
                stage('Frontend/prepare') {
                    sh "npm install"
                }
                stage('Frontend/build') {
                    sh "npm run build"
                }
            }
        }
    }

    parallel 'Checkstyle': {
        sh "'${mvnHome}/bin/mvn' checkstyle:checkstyle"
        checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/checkstyle-result.xml', unHealthy: ''
    }, 'Findbugs': {
        sh "'${mvnHome}/bin/mvn' findbugs:findbugs"
        findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: 'target/findbugsXml.xml', unHealthy: ''
    }, 'PMD': {
        sh "'${mvnHome}/bin/mvn' pmd:pmd"
        pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/pmd.xml', unHealthy: ''
    }

    stage('Publishing reports') {
        step([$class: 'AnalysisPublisher'])
    }

    archiveArtifacts artifacts: '**/*.war'

}
