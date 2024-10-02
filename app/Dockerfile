# Use a Maven image to build the project
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .

# Download dependencies (this will cache Maven dependencies if no changes in pom.xml)
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests



# use a minimal JDK image to run the application
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
