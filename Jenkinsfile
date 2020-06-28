pipeline {
  agent any

  environment {
        NEXUS_VERSION = "nexus3"
        NEXUS_PROTOCOL = "http"
        NEXUS_URL = "localhost:8081"
        NEXUS_REPOSITORY = "Declarative-pipeline"
        NEXUS_CREDENTIAL_ID = "nexusid"
        
    }

  stages 
  {
    
      stage('Sonar Analysis') {
        steps{
    
        bat 'mvn sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=b90a3548447883f6aba5eda092c18ac2909ed4f1'
      }
}
    stage('Compile') {
      steps {
        bat 'mvn compile'
      }
    }
      stage('Package') {
      steps {
        bat 'mvn package'
      }
    }
      stage('Install') {
      steps {
        bat 'mvn install'
      }
    }
      stage("Artifacts to Nexus Repository Manager") {
            steps {
                script {
                    pom = readMavenPom file: "pom.xml";
                    filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                    echo "${filesByGlob[0].name} ${filesByGlob[0].path} ${filesByGlob[0].directory} ${filesByGlob[0].length} ${filesByGlob[0].lastModified}"
                    artifactPath = filesByGlob[0].path;
                    artifactExists = fileExists artifactPath;
                    if(artifactExists) {
                        echo "*** File: ${artifactPath}, group: ${pom.groupId}, packaging: ${pom.packaging}, version ${pom.version}";
                        nexusArtifactUploader(
                            nexusVersion: NEXUS_VERSION,
                            protocol: NEXUS_PROTOCOL,
                            nexusUrl: NEXUS_URL,
                            groupId: pom.groupId,
                            version: pom.version,
                            repository: NEXUS_REPOSITORY,
                            credentialsId: NEXUS_CREDENTIAL_ID,
                            artifacts: [
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: artifactPath,
                                type: pom.packaging],
                                [artifactId: pom.artifactId,
                                classifier: '',
                                file: "pom.xml",
                                type: "pom"]
                            ]
                        );
                    } else {
                        error "*** File: ${artifactPath}, could not be found";
                    }
                }
            }
        }

stage('Deploy to Tomcat'){
            steps{
            
                sh "curl -v -u admin:admin -T C:/Users/ishaa/.jenkins/workspace/pipe-test/target/temporary-0.0.1-SNAPSHOT.war 'http://localhost:9090/manager/text/deploy?path=/Declarative-pipeline&update=true'"
            }
        }
    
  }
}
