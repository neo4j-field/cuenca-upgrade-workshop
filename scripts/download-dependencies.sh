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
