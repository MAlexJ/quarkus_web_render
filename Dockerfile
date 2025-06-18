# Build stage
FROM eclipse-temurin:24.0.1_9-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN --mount=type=cache,target=/root/.gradle  chmod +x gradlew && ./gradlew clean build -x test

# Runtime stage
FROM eclipse-temurin:24.0.1_9-jdk-alpine

WORKDIR /app

# Copy Quarkus output
COPY --from=builder /app/build/quarkus-app/lib/ /app/lib/
COPY --from=builder /app/build/quarkus-app/*.jar /app/
COPY --from=builder /app/build/quarkus-app/app/ /app/app/
COPY --from=builder /app/build/quarkus-app/quarkus/ /app/quarkus/
COPY --from=builder /app/build/quarkus-app/quarkus-run.jar /app/quarkus-run.jar

#ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT}"
# Set JVM options
ENV JAVA_OPTS="-Xms512m -Xmx512m -XX:MaxDirectMemorySize=512m"

# Start Quarkus with the configured options
CMD ["sh", "-c", "java $JAVA_OPTS -jar quarkus-run.jar"]
