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
        when { branch "master" }
        steps { 
	    sh ''' 
		docker run \
			-p 8090:8080 \
			--name ping \
			-t \
			-d motsdockerid/ping:latest \
			--mount source=logs, target=/apps/logs
	    ''' }
    }

    stage('Re-Run Docker image') {
        steps {
	    sh '''
		docker stop ping
    		docker rm ping
		docker run \
			-p 8090:8080 \
			--name ping \
			-t \
			-d motsdockerid/ping:latest \
			--mount source=logs, target=/apps/logs
    	    '''
	}
    }
    stage('Clean Docker images?') {
        when { branch "master" }
        steps { sh ''' docker rmi -f $(docker images -q --filter dangling=true) ''' }
    }


}
}