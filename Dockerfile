# Build stage
FROM gradle:8.13.0-jdk21-alpine AS builder
WORKDIR /app
COPY . .
RUN --mount=type=cache,target=/root/.gradle  chmod +x gradlew && ./gradlew clean build -x test

# Runtime stage
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy Quarkus output
COPY --from=builder /app/build/quarkus-app/lib/ /app/lib/
COPY --from=builder /app/build/quarkus-app/*.jar /app/
COPY --from=builder /app/build/quarkus-app/app/ /app/app/
COPY --from=builder /app/build/quarkus-app/quarkus/ /app/quarkus/
COPY --from=builder /app/build/quarkus-app/quarkus-run.jar /app/quarkus-run.jar

ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT}"

CMD ["java", "-jar", "quarkus-run.jar"]
