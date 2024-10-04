#!/usr/bin/env bash

set -Eeuo pipefail

script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd -P)

trap cleanup SIGINT SIGTERM ERR EXIT
cleanup() {
  trap - SIGINT SIGTERM ERR EXIT
  rm "$script_dir"/pom.xml
}

cd "$script_dir"
cp "$script_dir"/../app/pom.xml .
mvn dependency:go-offline

sed -i '' 's/<version>2.7.18<\/version>/<version>3.3.4<\/version>/' pom.xml
mvn dependency:go-offline

cp "$script_dir"/../plugin/pom.xml .
mvn dependency:go-offline

sed -i '' 's/<neo4j.version>4.4.37<\/neo4j.version>/<neo4j.version>5.23.0<\/neo4j.version>/' pom.xml
mvn dependency:go-offline

docker_images=("neo4j:4.4.37-enterprise" "neo4j:5.23.0-enterprise" "eclipse-temurin:17-jre-alpine")
for image in "${docker_images[@]}"; do
  docker pull "$image"
done