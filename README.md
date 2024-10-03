# cuenca-upgrade-workshop

During this exercise, you'll need to migrate a solution from Neo4j 4 to Neo4j 5.

Then, the customer willing to

This solution is composed of:

- a Neo4j database deployed on a cluster (3 primaries + 1 secondary)
- data loading scripts
- a Neo4j custom plugin
- a Spring boot based application

## Preparation work / pre-requisites

1. Install required software:

- Java 11 AND Java 17
- Maven
- A decent IDE
- Docker environment such as docker desktop (ask IT)
  or [colima](https://mvmn.wordpress.com/2023/10/26/switching-from-docker-desktop-to-colima-on-macos/)

2. Download the maven dependencies of the example application, as this can take a while:

```bash
./scripts/download-dependencies.sh
```

3. Deploy the Neo4j cluster as described below, to download the docker images

## Setup

### Deploy the Neo4j cluster

```bash
docker compose -f neo4j-cluster.yml up
```

### Load initial data

```bash
./scripts/load_data.sh
```

### Run the example app

Different options here

- Build and run with maven (recommended)

```bash
cd app && mvn spring-boot:run
```

- Build and run in a docker container

```bash
docker compose -f app/app.yml up
```

You can now access the application using these 2 endpoints:

```bash
curl http://localhost:8080/
```

```bash
curl http://localhost:8080/bus/route/43/stops?direction=outbound
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

