# cuenca-upgrade-workshop

##setup

Deploy the Neo4j cluster:

```
docker compose -f neo4j-cluster.yml up
```


Load initial data:

```
./scripts/load_data.sh
```

Run the app:

```
TBD
docker compose -f app/app.yml up
```

or `mvn run`


## Goals

1. Upgrade
- upgrade the neo4j cluster to v5 (preserving its existing data)
- upgrade the application
- upgrade the scripts

2. move to Aura
- replace the local cluster by AuraDB, preserving the existing data
- point the application to Aura
- fix the data loading script


Use the PS tenant:
  Neo4j PS Team EMEA (GCP) [f18e9993-71ac-528b-82dc-2cdc2c76087b] (login with first.last+enterprise@neo4j.com)

