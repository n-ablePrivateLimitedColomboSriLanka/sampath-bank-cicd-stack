def github_project_url = 'https://github.com/nable-integration-cicd-dev-mirror/JenkinsJobDSL.git'
pipelineJob('JobCreator') {
  parameters {
	stringParam('repository_name', '', 'Name of the job being generated - equals to the repository name')
	stringParam('trunk_branch', 'master', 'The trunk branch to which feature branches are merged to')
  }
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
                key('repository_name')
                value('$.repository.name')
                expressionType('JSONPath')
              }
              genericVariable {
                key('before')
                value('$.before')
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
            regexpFilterExpression('(push:0000000000000000000000000000000000000000|installation:.*created)')
            regexpFilterText('$x_github_event:$before$action')
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
