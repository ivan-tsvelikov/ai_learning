#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")/.."
: "${DB_PASSWORD:?Set DB_PASSWORD in this terminal before starting the project database}"
docker compose up -d --wait db
exec sh ./mvnw quarkus:dev
