#!/bin/bash

source .env

docker compose --env-file ".env.${ENVIRONMENT}" up --remove-orphans -d