#!/usr/bin/env bash
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

docker run --interactive --tty --rm \
    --volume=${SCRIPT_DIR}:/scripts \
    --env NEO4J_ACCEPT_LICENSE_AGREEMENT=yes \
    --network=cuenca_db_cluster_net  \
    neo4j/neo4j-admin:4.4.37-enterprise \
    cypher-shell -u neo4j -d neo4j -f /scripts/load.cypher -a neo4j://neo4j1:7687