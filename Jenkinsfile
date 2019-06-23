def CONTAINER_NAME="ratpack-sample-app"
def CONTAINER_TAG="latest"
def DOCKER_HUB_USER="sundaravelb"
def HTTP_PORT="5050"

node {

 try {
    
    stage('Initialize'){
        def dockerHome = tool 'myDocker'
        def gradleHome  = tool 'myGradle'
        env.PATH = "${dockerHome}/bin:${gradleHome}/bin:${env.PATH}"
    }

    stage('Checkout') {
        checkout scm
    }

    stage('Build'){
        sh "gradle build"
    }

    /* stage('Sonar'){
        try {
            sh "mvn sonar:sonar"
        } catch(error){
            echo "The sonar server could not be reached ${error}"
        }
     } */

    stage("Image Prune"){
        imagePrune(CONTAINER_NAME)
    }

    stage('Image Build'){
        imageBuild(CONTAINER_NAME, CONTAINER_TAG)
    }

    stage('Push to Docker Registry'){
        withCredentials([usernamePassword(credentialsId: 'dockerHubCred', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            pushToImage(CONTAINER_NAME, CONTAINER_TAG, USERNAME, PASSWORD)
        }
    }

    stage('Run App'){
        runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT)
    }

    } catch (e) {
     currentBuild.result = "FAILED"
     notifyFailed()
     throw e
   }
 }

}

def imagePrune(containerName){
    try {
        sh "docker image prune -f"
        sh "docker stop $containerName"
    } catch(error){}
}

def imageBuild(containerName, tag){
    sh "docker build -t $containerName:$tag  -t $containerName --pull --no-cache ."
    echo "Image build complete"
}

def pushToImage(containerName, tag, dockerUser, dockerPassword){
    sh "docker login -u $dockerUser -p $dockerPassword"
    sh "docker tag $containerName:$tag $dockerUser/$containerName:$tag"
    sh "docker push $dockerUser/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPort){
    sh "docker pull $dockerHubUser/$containerName"
    sh "docker run -d --rm -p $httpPort:$httpPort --name $containerName $dockerHubUser/$containerName:$tag"
    echo "Ratpack Application started"
}

def notifyFailed() {
   slackSend (color: '#FF0000', message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})")
 
   hipchatSend (color: 'RED', notify: true,
       message: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})"
     )
 
   emailext (
       subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
       body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
         <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
       recipientProviders: [[$class: 'DevelopersRecipientProvider']]
     )
 }
