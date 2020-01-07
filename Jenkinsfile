#!/usr/bin/env groovy
pipeline {
    agent any
    options { disableConcurrentBuilds() }
    stages {
        stage('Permissions') {
            steps {
                sh 'chmod 775 *'
            }
        }

    stage('Build') {
	steps {  sh './gradlew build' }
    }

    stage('Build Docker image') {
        steps { sh ''' docker build --no-cache -t ping .  ''' }
    }

    stage('Tag Docker image') {
        steps { sh ''' docker tag ping:latest motsdockerid/ping:latest ''' }
    }

    stage('Run Docker image First time') {
        steps { sh ''' docker run -p 8090:8080 --name ping -t -d motsdockerid/ping:latest -v /apps:/apps:rw ''' }
    }

    stage('Re-Run Docker image') {
        when { branch "master" }
        steps {
	    sh '''
		docker stop hello
    		docker rm ping
    		docker run -p 8090:8080 --name ping -t -d motsdockerid/ping:latest -v /apps:/apps:rw
    	    '''
	}
    }
    stage('Clean Docker images?') {
        when { branch "master" }
        steps { sh ''' docker rmi -f $(docker images -q --filter dangling=true) ''' }
    }


}
}