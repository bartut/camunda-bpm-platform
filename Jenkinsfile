// https://github.com/camunda/jenkins-global-shared-library
@Library('camunda-ci') _

String getAgent(String dockerImage = 'gcr.io/ci-30-162810/centos:v0.4.6', Integer cpuLimit = 4){
  String mavenForkCount = cpuLimit;
  String mavenMemoryLimit = cpuLimit * 2;
  """
metadata:
  labels:
    agent: ci-cambpm-camunda-cloud-build
spec:
  nodeSelector:
    cloud.google.com/gke-nodepool: agents-n1-standard-32-netssd-preempt
  tolerations:
  - key: "agents-n1-standard-32-netssd-preempt"
    operator: "Exists"
    effect: "NoSchedule"
  containers:
  - name: "jnlp"
    image: "${dockerImage}"
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
    tty: true
    env:
    - name: LIMITS_CPU
      value: ${mavenForkCount}
    - name: TZ
      value: Europe/Berlin
    resources:
      limits:
        cpu: ${cpuLimit}
        memory: ${mavenMemoryLimit}Gi
      requests:
        cpu: ${cpuLimit}
        memory: ${mavenMemoryLimit}Gi
    workingDir: "/home/work"
    volumeMounts:
      - mountPath: /home/work
        name: workspace-volume
  """
}

pipeline {
  agent none
  options {
    buildDiscarder(logRotator(numToKeepStr: '5')) //, artifactNumToKeepStr: '30'
    copyArtifactPermission('*');
  }
  stages {
//    stage('Prepare') {
//      agent {
//        kubernetes {
//          yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//        }
//      }
//      steps {
//        withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
//          nodejs('nodejs-14.6.0'){
//             configFileProvider([configFile(fileId: 'maven-nexus-settings', variable: 'MAVEN_SETTINGS_XML')]) {
//               sh """
//                 mvn -s \$MAVEN_SETTINGS_XML clean install source:jar -Pdistro,distro-ce,distro-wildfly,distro-webjar -DskipTests -Dmaven.repo.local=\$(pwd)/.m2 com.mycila:license-maven-plugin:check -B
//               """
//             }
//          }
//
//          archiveArtifacts artifacts: '.m2/org/camunda/**/*-SNAPSHOT/**/*.jar,.m2/org/camunda/**/*-SNAPSHOT/**/*.pom,.m2/org/camunda/**/*-SNAPSHOT/**/*.xml,.m2/org/camunda/**/*-SNAPSHOT/**/*.txt,.m2/org/camunda/**/*-SNAPSHOT/**/camunda-webapp*frontend-sources.zip', followSymlinks: false
//          archiveArtifacts artifacts: '.m2/org/camunda/**/camunda-webapp*frontend-sources.zip,.m2/org/camunda/**/license-book*.zip,.m2/org/camunda/**/camunda-webapp*.war,.m2/org/camunda/**/camunda-engine-rest*.war,.m2/org/camunda/**/camunda-example-invoice*.war,.m2/org/camunda/**/camunda-h2-webapp*.war,.m2/org/camunda/**/*-SNAPSHOT/**/*.tar.gz,.m2/org/camunda/**/*-SNAPSHOT/**/camunda-jboss-modules*.zip', followSymlinks: false
//
//          stash name: "platform-stash-runtime", includes: ".m2/org/camunda/**/*-SNAPSHOT/**", excludes: "**/qa/**,**/*qa*/**,**/*.zip,**/*.tar.gz"
//          stash name: "platform-stash-qa", includes: ".m2/org/camunda/bpm/**/qa/**/*-SNAPSHOT/**,.m2/org/camunda/bpm/**/*qa*/**/*-SNAPSHOT/**", excludes: "**/*.zip,**/*.tar.gz"
//          stash name: "platform-stash-distro", includes: ".m2/org/camunda/bpm/**/*-SNAPSHOT/**/*.zip,.m2/org/camunda/bpm/**/*-SNAPSHOT/**/*.tar.gz"
//         }
//
//        build job: "cambpm-jenkins-pipelines-ee/${env.BRANCH_NAME}", parameters: [
//            string(name: 'copyArtifactSelector', value: '<TriggeredBuildSelector plugin="copyartifact@1.45.1">  <upstreamFilterStrategy>UseGlobalSetting</upstreamFilterStrategy>  <allowUpstreamDependencies>false</allowUpstreamDependencies></TriggeredBuildSelector>'),
//            booleanParam(name: 'STANDALONE', value: false)
//        ], quietPeriod: 10, wait: false
//        build job: "cambpm-jenkins-pipelines-daily/${env.BRANCH_NAME}", parameters: [
//            string(name: 'copyArtifactSelector', value: '<TriggeredBuildSelector plugin="copyartifact@1.45.1">  <upstreamFilterStrategy>UseGlobalSetting</upstreamFilterStrategy>  <allowUpstreamDependencies>false</allowUpstreamDependencies></TriggeredBuildSelector>'),
//            booleanParam(name: 'STANDALONE', value: false)
//        ], quietPeriod: 10, wait: false
//
//      }
//    }
//    stage('h2 tests') {
//      parallel {
//        stage('engine-UNIT-h2') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('h2')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'engine/', ' test -Pdatabase,h2')
//            }
//          }
//        }
//        stage('engine-UNIT-authorizations-h2') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('h2')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'engine/', 'test -Pdatabase,h2,cfgAuthorizationCheckRevokesAlways')
//            }
//          }
//        }
//        stage('engine-rest-UNIT-jersey-2') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('rest')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'engine-rest/engine-rest/', 'clean install -Pjersey2')
//            }
//          }
//        }
//        stage('engine-rest-UNIT-resteasy3') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('rest')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'engine-rest/engine-rest/', 'clean install -Presteasy3')
//            }
//          }
//        }
//        stage('webapp-UNIT-h2') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('webapps')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'webapps/', 'clean test -Pdatabase,h2 -Dskip.frontend.build=true')
//            }
//          }
//        }
//        stage('engine-IT-tomcat-9-h2') {// TODO change it to `postgresql_96`
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('IT')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
//                runMaven(true, true, false, 'qa/', 'clean install -Ptomcat,h2,engine-integration')
//              }
//            }
//          }
//          post {
//            always {
//              junit testResults: '**/target/*-reports/TEST-*.xml', keepLongStdio: true
//            }
//          }
//        }
//        stage('webapp-IT-tomcat-9-h2') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('webapps', 'IT')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/chrome:78v0.1.2')
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
//                runMaven(true, true, false, 'qa/', 'clean install -Ptomcat,h2,webapps-integration')
//              }
//            }
//          }
//          post {
//            always {
//              junit testResults: '**/target/*-reports/TEST-*.xml', keepLongStdio: true
//            }
//          }
//        }
//        stage('webapp-IT-standalone-wildfly') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('webapps', 'IT')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/chrome:78v0.1.2')
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
//                runMaven(true, true, false, 'qa/', 'clean install -Pwildfly-vanilla,webapps-integration-sa')
//              }
//            }
//          }
//        }
//        stage('camunda-run-IT') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('IT', 'run', 'spring-boot')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/chrome:78v0.1.2', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
//                runMaven(true, true, true, 'distro/run/', 'clean install -Pintegration-test-camunda-run')
//              }
//            }
//          }
//          post {
//            always {
//              junit testResults: '**/target/*-reports/TEST-*.xml', keepLongStdio: true
//            }
//          }
//        }
//        stage('spring-boot-starter-IT') {
//          when {
//            anyOf {
//              branch 'pipeline-master';
//              allOf {
//                changeRequest();
//                expression {
//                  withLabels('IT', 'spring-boot')
//                }
//              }
//            }
//          }
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/chrome:78v0.1.2', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
//                runMaven(true, true, true, 'spring-boot-starter/', 'clean install -Pintegration-test-spring-boot-starter')
//              }
//            }
//          }
//          post {
//            always {
//              junit testResults: '**/target/*-reports/TEST-*.xml', keepLongStdio: true
//            }
//          }
//        }
//      }
//    }
    stage("engine-UNIT DB tests") {
      matrix {
        axes {
          axis {
            name 'DB'
            values 'postgresql_96'
          }
        }
//        when {
//          anyOf {
//            branch 'pipeline-master';
//            allOf {
//              changeRequest();
//              expression {
//                withLabels("all-db") || withDbLabel(env.DB)
//              }
//            }
//          }
//        }
        agent {
          kubernetes {
            yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16) + getDbAgent(env.DB)
          }
        }
        stages {
          stage("engine-UNIT") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
                runMaven(true, false, false, 'engine/', 'clean test -P' + getDbProfiles(env.DB) + " " + getDbExtras(env.DB))
              }
            }
          }
        }
      }
    }
//    stage("engine-UNIT-authorizations DB tests") {
//      matrix {
//        axes {
//          axis {
//            name 'DB'
//            values 'postgresql_96'
//          }
//        }
//        agent {
//          kubernetes {
//            yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16) + getDbAgent(env.DB)
//          }
//        }
//        stages {
//          stage("engine-UNIT-authorizations") {
//            steps {
//              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//                runMaven(true, false, false, 'engine/', 'clean test -PcfgAuthorizationCheckRevokesAlways' + getDbProfiles(env.DB) + " " + getDbExtras(env.DB))
//              }
//            }
//          }
//        }
//      }
//    }
//    stage("webapp-UNIT DB tests") {
//      matrix {
//        axes {
//          axis {
//            name 'DB'
//            values 'postgresql_96'
//          }
//        }
//        agent {
//          kubernetes {
//            yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16) + getDbAgent(env.DB)
//          }
//        }
//        stages {
//          stage("webapp-UNIT") {
//            steps {
//              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//                runMaven(true, false, false, 'webapps/', 'clean test -Dskip.frontend.build=true -P' + getDbProfiles(env.DB) + " " + getDbExtras(env.DB))
//              }
//            }
//          }
//        }
//      }
//    }
//    stage("webapp-UNIT-authorizations DB tests") {
//      matrix {
//        axes {
//          axis {
//            name 'DB'
//            values 'postgresql_96'
//          }
//        }
//        agent {
//          kubernetes {
//            yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16) + getDbAgent(env.DB)
//          }
//        }
//        stages {
//          stage("webapp-UNIT-authorizations") {
//            steps {
//              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//                runMaven(true, false, false, 'webapps/', 'clean test -Dskip.frontend.build=true -PcfgAuthorizationCheckRevokesAlways' + getDbProfiles(env.DB) + " " + getDbExtras(env.DB))
//              }
//            }
//          }
//        }
//      }
//    }
//    stage('db tests + CE webapps IT + EE platform') {
//      parallel {
//        stage('engine-api-compatibility') {
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'engine/', 'clean verify -Pcheck-api-compatibility')
//            }
//          }
//        }
//        stage('engine-UNIT-plugins') {
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'engine/', 'clean test -Pcheck-plugins')
//            }
//          }
//        }
//        stage('engine-UNIT-database-table-prefix') {
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, false, 'engine/', 'clean test -Pdb-table-prefix')
//            }
//          }
//        }
//        stage('webapp-UNIT-database-table-prefix') {
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              nodejs('nodejs-14.6.0'){
//                runMaven(true, false, false, 'webapps/', 'clean test -Pdb-table-prefix')
//              }
//            }
//          }
//        }
//        stage('engine-UNIT-wls-compatibility') {
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, false, true, '.', 'clean verify -Pcheck-engine,wls-compatibility,jersey')
//            }
//          }
//        }
//        stage('IT-wildfly-domain') {
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, true, true, 'qa/', 'clean install -Pwildfly-domain,h2,engine-integration')
//            }
//          }
//        }
//        stage('IT-wildfly-servlet') {
//          agent {
//            kubernetes {
//              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
//            }
//          }
//          steps{
//            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'camunda-public-maven-settings') {
//              runMaven(true, true, true, 'qa/', 'clean install -Pwildfly,wildfly-servlet,h2,engine-integration')
//            }
//          }
//        }
////        stage('EE-platform-DISTRO-dummy') {
////          agent {
////            kubernetes {
////              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
////            }
////          }
////          steps{
////            withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest') {
////            }
////          }
////        }
//      }
//    }
  }
  post {
    changed {
      script {
        if (!agentDisconnected()){ 
          // send email if the slave disconnected
        }
      }
    }
    always {
      script {
        if (agentDisconnected()) {// Retrigger the build if the slave disconnected
          //currentBuild.result = 'ABORTED'
          //currentBuild.description = "Aborted due to connection error"
          build job: currentBuild.projectName, propagate: false, quietPeriod: 60, wait: false
        }
      }
    }
  }
}

void runMaven(boolean runtimeStash, boolean distroStash, boolean qaStash, String directory, String cmd) {
//  if (runtimeStash) unstash "platform-stash-runtime"
//  if (distroStash) unstash "platform-stash-distro"
//  if (qaStash) unstash "platform-stash-qa"
  configFileProvider([configFile(fileId: 'maven-nexus-settings', variable: 'MAVEN_SETTINGS_XML')]) {
    sh("mvn -s \$MAVEN_SETTINGS_XML ${cmd} -nsu -Dmaven.repo.local=\${WORKSPACE}/.m2 -DforkCount=1 -f ${directory}/pom.xml -B")
  }
}

void withLabels(String... labels) {
  for ( l in labels) {
    pullRequest.labels.contains(labelName)
  }
}

void withDbLabels(String dbLabel) {
  withLabels(getDbType(dbLabel))
}

String getDbAgent(String dbLabel, Integer cpuLimit = 4, Integer mavenForkCount = 1){
  Map dbInfo = getDbInfo(dbLabel)
  String mavenMemoryLimit = cpuLimit * 4;
  """
metadata:
  labels:
    name: "${dbLabel}"
    jenkins: "slave"
    jenkins/label: "jenkins-slave-${dbInfo.type}"
spec:
  containers:
  - name: "jnlp"
    image: "gcr.io/ci-30-162810/${dbInfo.type}:${dbInfo.version}"
    args: ['\$(JENKINS_SECRET)', '\$(JENKINS_NAME)']
    tty: true
    env:
    - name: LIMITS_CPU
      value: ${mavenForkCount}
    - name: TZ
      value: Europe/Berlin
    resources:
      limits:
        memory: ${mavenMemoryLimit}Gi
      requests:
        cpu: ${cpuLimit}
        memory: ${mavenMemoryLimit}Gi
    volumeMounts:
    - mountPath: "/home/work"
      name: "workspace-volume"
    workingDir: "/home/work"
    nodeSelector:
      cloud.google.com/gke-nodepool: "agents-n1-standard-4-netssd-preempt"
    restartPolicy: "Never"
    tolerations:
    - effect: "NoSchedule"
      key: "agents-n1-standard-4-netssd-preempt"
      operator: "Exists"
    volumes:
    - emptyDir:
        medium: ""
      name: "workspace-volume"
  """
}

Map getDbInfo(String databaseLabel) {
  Map SUPPORTED_DBS = ['postgresql_96': [
                           type: 'postgresql',
                           version: '9.6v0.2.2',
                           profiles: 'postgresql',
                           extra: ''],
                       'mariadb_103': [
                           type: 'mariadb:',
                           version: '10.3v0.3.2',
                           profiles: 'mariadb',
                           extra: ''],
                       'sqlserver_2017': [
                           type: 'mssql',
                           version: '2017v0.1.1',
                           profiles: 'sqlserver',
                           extra: '-Ddatabase.name=camunda -Ddatabase.username=sa -Ddatabase.password=cam_123$']
  ]

  return SUPPORTED_DBS[databaseLabel]
}

String getDbType(String dbLabel) {
  String[] database = dbLabel.split("_")
  return database[0]
}

String getDbProfiles(String dbLabel) {
  return getDbInfo(dbLabel).profiles
}

String getDbExtras(String dbLabel) {
  return getDbInfo(dbLabel).extra
}