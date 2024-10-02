# cuenca-upgrade-workshop

## setup

Deploy the whole application:

```
docker compose -f deployment.yml up
```


Load initial data:

```
./scripts/load_data.sh
```

Run only the app:

```
docker compose -f deployment.yml --profile app  up
```

or `mvn spring-boot:run` to run locally.

Run only the db:

```
docker compose -f deployment.yml --profile db  up
```

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

