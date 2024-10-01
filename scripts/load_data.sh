docker exec --interactive --tty cuenca_db-neo4j1-1 \
    cypher-shell -u neo4j -d neo4j -f ./scripts/load.cypher -a neo4j://neo4j1:7687