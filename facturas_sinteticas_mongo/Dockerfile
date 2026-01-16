# Multi-stage build for Spring Boot app (Java 17)

# --- Build stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only pom.xml first to leverage dependency layer caching
COPY pom.xml .
# Pre-fetch dependencies (no source yet)
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -DskipTests dependency:go-offline

# Now copy sources and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -DskipTests clean package

# --- Runtime stage ---
FROM eclipse-temurin:17-jre-jammy AS runtime
WORKDIR /app

# Useful defaults (override at runtime if needed)
ENV JAVA_OPTS="" \
    SPRING_PROFILES_ACTIVE="default"

# Copy the fat jar from the build stage
ARG JAR_FILE=target/*.jar
COPY --from=build /app/${JAR_FILE} app.jar
# Also copy the application.properties into the runtime image for inspection
COPY --from=build /app/src/main/resources/application.properties application.properties

# Expose Spring Boot port (see application.properties -> 8083)
EXPOSE 8083

# Healthcheck (optional; adjust path if you have a readiness endpoint)
# HEALTHCHECK --interval=30s --timeout=3s CMD wget -qO- http://localhost:8083/actuator/health | grep '"status":"UP"' || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
