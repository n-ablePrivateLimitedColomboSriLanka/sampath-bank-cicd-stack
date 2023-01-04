def github_project_url = 'https://github.com/nable-integration-cicd-dev-mirror/JenkinsJobDSL.git'
pipelineJob('JobCreator') {
  properties {
    githubProjectUrl(github_project_url)
    parameters {
      pipelineTriggers {
        triggers {
          GenericTrigger {
            genericVariables {
              genericVariable {
                key('repository_full_name')
                value('$.repository.full_name')
                expressionType('JSONPath')
              }
              genericVariable {
                key('repository_clone_url')
                value('$.repository.clone_url')
                expressionType('JSONPath')
              }
              genericVariable {
                key('job_name')
                value('$.repository.name')
                expressionType('JSONPath')
              }
              genericVariable {
                key('action')
                value('$.action')
                expressionType('JSONPath')
              }
            }
            genericHeaderVariables {
              genericHeaderVariable {
                key('x-github-event')
                regexpFilter('')
              }
            }
            causeString('Github Repository Created')
            regexpFilterExpression('repository:created')
            regexpFilterText('$x_github_event:$action')
            tokenCredentialId('generic_webhook_token')
          }
        }
      }
    }
  }
  definition {
    cpsScm {
      lightweight(true)
      scm {
        git {
          remote {
            url(github_project_url)
            credentials('jenkins_github_app')
          }
          branch('*/master')
        }
      }
      scriptPath('Jenkinsfile')
    }
  }
}
