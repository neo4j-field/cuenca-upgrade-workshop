######### STAGE 1 ##################################################
# Use a Maven image to build the custom plugin
####################################################################
# FROM maven:3.9.9-eclipse-temurin-11-alpine AS build

# WORKDIR /plugin

# COPY plugin/pom.xml .

# # Download dependencies (this will cache Maven dependencies if no changes in pom.xml)
# RUN mvn dependency:go-offline

# #build the plugin
# COPY plugin/src ./src
# RUN mvn clean package -DskipTests


######### STAGE 2 ##################################################
# Customise the official neo4j image by adding the required plugins
####################################################################
FROM neo4j:4.4.37-enterprise

USER neo4j

# Install custom plugin
# COPY --from=build /plugin/target/custom-1.0.0.jar plugins/custom-1.0.0.jar

# Copy externally built custom plugin
COPY plugin/target/custom-1.0.0.jar plugins/custom-1.0.0.jar


# Install APOC plugin
ENV NEO4J_APOC_VERSION='4.4.0.31'
RUN wget -O "plugins/apoc-$NEO4J_APOC_VERSION-all.jar" "https://github.com/neo4j-contrib/neo4j-apoc-procedures/releases/download/$NEO4J_APOC_VERSION/apoc-$NEO4J_APOC_VERSION-all.jar"


COPY scripts ./scripts