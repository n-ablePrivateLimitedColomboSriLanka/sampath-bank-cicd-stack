#!/bin/bash

source .env

docker compose --env-file ".env.${ENVIRONMENT}" up -d