#!/bin/bash
SCRIPT_ROOT="$( cd -- "$(dirname "$0")" >/dev/null 2>&1 ; pwd -P )"
SECRETS_DIR=$SCRIPT_ROOT


# Generate a random string of a given length
function random_string() {
  cat /dev/urandom | tr -dc 'a-zA-Z0-9!@#$&' | fold -w $1 | head -n 1
}

# Return the list of secret names used for CICD stack
function secret_names() {
  secret_name=(generic-webhook-token nexus-jenkins-user-password nexus-realease-reader-user-password)
  echo "${secret_name[@]}"
}

# Write secrets to a files
function generate_secrets() {
  secret_names=($(secret_names))
  for secret_name in "${secret_names[@]}"; do
    secret_file=$SECRETS_DIR/$secret_name
    echo $(random_string 32) > $secret_file
  done
  
  echo jenkins > $SECRETS_DIR/nexus-jenkins-user-id
  echo release-reader > $SECRETS_DIR/nexus-release-reader-user-id
  touch $SECRETS_DIR/github-app-{id,privkey}
}

# DO THE MAGIC
generate_secrets