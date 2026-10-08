# Build the Spring Boot jar
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests package

# Run the packaged application
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends wget \
    && rm -rf /var/lib/apt/lists/* \
    && groupadd --system spring \
    && useradd --system --gid spring --home /app spring \
    && chown spring:spring /app
USER spring

COPY --from=build --chown=spring:spring /app/target/geo-tracking-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Defaults match application.yaml, but point at the Docker host so Postgres
# and Redis on the machine are reachable. Override these when the databases
# run in other containers.
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/geo_tracking \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=root \
    SPRING_DATA_REDIS_HOST=host.docker.internal \
    SPRING_DATA_REDIS_PORT=6379

HEALTHCHECK --interval=30s --timeout=5s --start-period=40s --retries=3 \
    CMD wget -qO- http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]
