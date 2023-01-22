def github_project_url = JOB_CREATOR_DSL_REPO

listView('Meta') {
    description('All Meta Jobs')
	jobFilters {
        regex {
            matchType(MatchType.INCLUDE_MATCHED)
            matchValue(RegexMatchValue.DESCRIPTION)
            regex('.*labels:.*meta.*')
        }
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}

listView('Java') {
    description('All Java Projects')
	jobFilters {
        regex {
            matchType(MatchType.INCLUDE_MATCHED)
            matchValue(RegexMatchValue.DESCRIPTION)
            regex('.*labels:.*java.*')
        }
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}

listView('ACE') {
    description('All AppConnect Enterprise Projects')
	jobFilters {
        regex {
            matchType(MatchType.INCLUDE_MATCHED)
            matchValue(RegexMatchValue.DESCRIPTION)
            regex('.*labels:.*ace.*')
        }
    }
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
        buildButton()
    }
}

pipelineJob('JobCreator') {
  description("labels:meta")
  parameters {
	stringParam('repository_name', '', 'Name of the job being generated - equals to the repository name')
	stringParam('trunk_branch', TRUNK_BRANCH_NAME, 'The trunk branch to which feature branches are merged to')
	stringParam('x_github_event', 'none', 'Do not edit the default value')
	stringParam('github_app_cred_id', GITHUB_APP_CRED_ID, 'Do not edit the default value')
	stringParam('github_org_name', GITHUB_ORG_NAME, 'Do not edit the default value')
	stringParam('build_scripts_repo', BUILD_SCRIPTS_REPO, 'Do not edit the default value')
	stringParam('ace_shared_lib_index_url', ACE_SHARED_LIB_INDEX_URL, 'Do not edit the default value')
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
            credentials(GITHUB_APP_CRED_ID)
          }
          branch('*/master')
        }
      }
      scriptPath('Jenkinsfile')
    }
  }
}
