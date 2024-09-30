
docker run --interactive --tty --rm \
    --volume=./backups:/backups \
    --env NEO4J_ACCEPT_LICENSE_AGREEMENT=yes \
    --network=cuenca_db_cluster_net  \
    neo4j/neo4j-admin:4.4.37-enterprise \
neo4j-admin backup --backup-dir=/backups --from=neo4j4:6362 --database=neo4j --include-metadata=all