#!groovy

@Library('jenkinslib') _     

def mytools = new org.devops.tools()

//https://github.com/Felyne/jenkinslib/blob/master/vars/sayBye.groovy
sayStart()

String workspace = "/opt/jenkins/workspace/devops/test1"

pipeline {
    agent {
        node {
            label 'build01'
            //customWorkspace "${workspace}"
        }
    }

    parameters {
        string(name:'TAG_NAME',defaultValue: 'v0.0.1',description:'') //定义 流水线描述
        booleanParam(name: 'DEBUG_BUILD', defaultValue: true, description: '')
        booleanParam(name: 'CODE_SCAN_ON', defaultValue: false, description: '')
    }

    environment {
        DOCKER_CREDENTIAL_ID = 'docker-hub'
        GITHUB_CREDENTIAL_ID = 'github-id'
    }

    options {
        timestamps() // 日志会有时间
        skipDefaultCheckout()
        disableConcurrentBuilds()
        timeout(time: 1, unit: 'HOURS')
    }

    stages {
        stage("GetCode"){ 
            steps{  
                timeout(time:5, unit:"MINUTES"){   
                    script{ 
                        mytools.PrintMes("获取代码",'green')
                    }
                }
            }
        }
        stage('Example'){
            input {
                message "Should we continue?"
                ok "Yes, we should."
                submitter "alice,bob"
                parameters {
                    string(name: 'PERSON', defaultValue: 'Mr jenkins', description: 'Who should I say hello to?', trim: false)
                    choice(choices: ['on', 'off'], description: '', name: 'TURN')
                }
            }
            steps {
                echo "Hello world $PERSON $TURN"
                sh 'echo "tag name: $TAG_NAME"'
                sh 'echo haha >> 1.txt'
                withCredentials([usernamePassword(passwordVariable : 'DOCKER_PASSWORD' ,usernameVariable : 'DOCKER_USERNAME' ,credentialsId : "$DOCKER_CREDENTIAL_ID" ,)]) {
                    sh 'echo "docker hub: $DOCKER_USERNAME $DOCKER_PASSWORD" >  2.txt'
                    sh 'cat 2.txt'
                }
                sh 'sleep 1'
            }
            
        }
        stage('CodeScan') {
            when { expression { return params.CODE_SCAN_ON } }
            steps {
                timeout(time:30, unit:'MINUTES') {
                    script {
                        print("代码扫描")   
                    }
                }
            }
        }
    }
    post {
        always {
            echo "BUILD_NUMBER is ${BUILD_NUMBER}"
            echo "BUILD_DISPLAY_NAME is ${BUILD_DISPLAY_NAME}"
            echo "JOB_NAME is ${JOB_NAME}"
            echo "BUILD_TAG is ${BUILD_TAG}"
            echo "NODE_NAME is ${NODE_NAME}"
            echo "TAG_NAME is ${TAG_NAME}"
            echo "BUILD_TIMESTAMP is ${BUILD_TIMESTAMP}"
            script {
                values = [env.BUILD_TIMESTAMP, currentBuild.durationString, workspace]
                printf("开始时间: %s, 耗时: %s, 工作目录: %s",  values)
            }
        }
        success {
            script {
                currentBuild.description += "\n 构建成功"
            }
        }
        failure {
            script {
                currentBuild.description += "\n 构建失败"
            }
        }
        aborted {
            script {
                currentBuild.description += "\n 构建取消"
            }
        }
    }
}