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
        }

        stage('Backend/package') {
            sh "'${mvnHome}/bin/mvn' package -Dmaven.main.skip -Dmaven.test.skip"
        }
    }, 'Code analysis': {
        stage('Checkstyle') {
            sh "'${mvnHome}/bin/mvn' checkstyle:checkstyle"
            checkstyle canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/checkstyle-result.xml', unHealthy: ''
        }
        stage('Findbugs') {
            sh "'${mvnHome}/bin/mvn' findbugs:findbugs"
            findbugs canComputeNew: false, defaultEncoding: '', excludePattern: '', healthy: '', includePattern: '', pattern: 'target/findbugsXml.xml', unHealthy: ''
        }
        stage('PMD') {
            sh "'${mvnHome}/bin/mvn' pmd:pmd"
            pmd canComputeNew: false, defaultEncoding: '', healthy: '', pattern: 'target/pmd.xml', unHealthy: ''
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
}
