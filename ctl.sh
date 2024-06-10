#!/bin/bash

source .env

BASE_CMD="docker compose --env-file .env.${ENVIRONMENT}"

function start() {
    $BASE_CMD up -d --remove-orphans
}

function stop() {
    $BASE_CMD down
}

$1