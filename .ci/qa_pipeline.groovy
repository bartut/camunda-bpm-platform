#!/usr/bin/env groovy

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
  stages {
    stage('check-sql-scripts') {
      agent {
        kubernetes {
          yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
        }
      }
      steps {

      }
    }
    stage('H2 QA tests') {
      parallel {
        stage('sql-scripts-h2') {
          agent {
            kubernetes {
              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
            }
          }
          when {
            anyOf {
              branch 'pipeline-qa-master';
              allOf {
                changeRequest();
                expression {
                  withLabels('h2')
                }
              }
            }
          }
          steps {

          }
        }
        stage('UPGRADE-databases-from-714-h2') {
          agent {
            kubernetes {
              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
            }
          }
          when {
            anyOf {
              branch 'pipeline-qa-master';
              allOf {
                changeRequest();
                expression {
                  withLabels('h2')
                }
              }
            }
          }
          steps {

          }
        }
        stage('UPGRADE-instance-migration-h2') {
          agent {
            kubernetes {
              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
            }
          }
          when {
            anyOf {
              branch 'pipeline-qa-master';
              allOf {
                changeRequest();
                expression {
                  withLabels('h2')
                }
              }
            }
          }
          steps {

          }
        }
        stage('UPGRADE-old-engine-from-714-h2') {
          agent {
            kubernetes {
              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
            }
          }
          when {
            anyOf {
              branch 'pipeline-qa-master';
              allOf {
                changeRequest();
                expression {
                  withLabels('h2')
                }
              }
            }
          }
          steps {

          }
        }
        stage('UPGRADE-rolling-update-h2') {
          agent {
            kubernetes {
              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
            }
          }
          when {
            anyOf {
              branch 'pipeline-qa-master';
              allOf {
                changeRequest();
                expression {
                  withLabels('h2')
                }
              }
            }
          }
          steps {

          }
        }
        stage('PERFORMANCE-large-data-h2') {
          agent {
            kubernetes {
              yaml getAgent('gcr.io/ci-30-162810/centos:v0.4.6', 16)
            }
          }
          when {
            anyOf {
              branch 'pipeline-qa-master';
              allOf {
                changeRequest();
                expression {
                  withLabels('h2')
                }
              }
            }
          }
          steps {

          }
        }
      }
    }
    stage('sql-scripts DBs') {
      matrix {
        axes {
          axis {
            name 'DB'
            values 'postgresql-96'
          }
        }
        when {
          anyOf {
            branch 'pipeline-qa-master';
            allOf {
              changeRequest();
              expression {
                withLabels("all-db") || withDbLabel(env.DB)
              }
            }
          }
        }
        stages {
          stage("") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
                runMaven(true, false, 'qa/', 'clean test -P' + getDbProfiles(env.DB))
              }
            }
          }
        }
      }
    }
    stage('UPGRADE-databases-from-714 DBs') {
      matrix {
        axes {
          axis {
            name 'DB'
            values 'postgresql-96'
          }
        }
        when {
          anyOf {
            branch 'pipeline-qa-master';
            allOf {
              changeRequest();
              expression {
                withLabels("all-db") || withDbLabel(env.DB)
              }
            }
          }
        }
        stages {
          stage("") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
                runMaven(true, false, 'qa/', 'clean test -P' + getDbProfiles(env.DB))
              }
            }
          }
        }
      }
    }
    stage('UPGRADE-instance-migration DBs') {
      matrix {
        axes {
          axis {
            name 'DB'
            values 'postgresql-96'
          }
        }
        when {
          anyOf {
            branch 'pipeline-qa-master';
            allOf {
              changeRequest();
              expression {
                withLabels("all-db") || withDbLabel(env.DB)
              }
            }
          }
        }
        stages {
          stage("") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
                runMaven(true, false, 'qa/', 'clean test -P' + getDbProfiles(env.DB))
              }
            }
          }
        }
      }
    }
    stage('UPGRADE-old-engine-from-714 DBs') {
      matrix {
        axes {
          axis {
            name 'DB'
            values 'postgresql-96'
          }
        }
        when {
          anyOf {
            branch 'pipeline-qa-master';
            allOf {
              changeRequest();
              expression {
                withLabels("all-db") || withDbLabel(env.DB)
              }
            }
          }
        }
        stages {
          stage("") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
                runMaven(true, false, 'qa/', 'clean test -P' + getDbProfiles(env.DB))
              }
            }
          }
        }
      }
    }
    stage('UPGRADE-rolling-update DBs') {
      matrix {
        axes {
          axis {
            name 'DB'
            values 'postgresql-96'
          }
        }
        when {
          anyOf {
            branch 'pipeline-qa-master';
            allOf {
              changeRequest();
              expression {
                withLabels("all-db") || withDbLabel(env.DB)
              }
            }
          }
        }
        stages {
          stage("") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
                runMaven(true, false, 'qa/', 'clean test -P' + getDbProfiles(env.DB))
              }
            }
          }
        }
      }
    }
    stage('PERFORMANCE-large-data DBs') {
      matrix {
        axes {
          axis {
            name 'DB'
            values 'postgresql-96'
          }
        }
        when {
          anyOf {
            branch 'pipeline-qa-master';
            allOf {
              changeRequest();
              expression {
                withLabels("all-db") || withDbLabel(env.DB)
              }
            }
          }
        }
        stages {
          stage("") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
                runMaven(true, false, 'qa/', 'clean test -P' + getDbProfiles(env.DB))
              }
            }
          }
        }
      }
    }
    stage('JDKs') {
      matrix {
        axes {
          axis {
            name 'JDK'
            values 'opend-jdk-8'
          }
        }
        when {
          anyOf {
            branch 'pipeline-qa-master';
            allOf {
              changeRequest();
              expression {
                withLabels("jdk")
              }
            }
          }
        }
        stages {
          stage("") {
            steps {
              withMaven(jdk: 'jdk-8-latest', maven: 'maven-3.2-latest', mavenSettingsConfig: 'maven-nexus-settings') {
                runMaven(true, false, 'qa/', 'clean test -P' + getDbProfiles(env.DB))
              }
            }
          }
        }
      }
    }
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

void runMaven(boolean runtimeStash, boolean distroStash, String directory, String cmd) {
  if (runtimeStash) unstash "platform-stash-runtime"
  if (distroStash) unstash "platform-stash-distro"
  configFileProvider([configFile(fileId: 'maven-nexus-settings', variable: 'MAVEN_SETTINGS_XML')]) {
    sh("export MAVEN_OPTS='-Dmaven.repo.local=\$(pwd)/.m2' && cd ${directory} && mvn -s \$MAVEN_SETTINGS_XML ${cmd} -B")
  }
}

void withLabels(String... labels) {
  for ( l in labels) {
    pullRequest.labels.contains(labelName)
  }
}