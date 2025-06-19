### Quarkus REST app for render

##### Description:

* Java 24
* QUARKUS 3.23.3
* Atlas Mongo db
* Gradle 8.14.2
* render - hosting service

### Project setup

create `.env` file with properties:

```
PORT=8080
TELEGRAM_WEB_APP_SECRET_KEY=___xxx___
TELEGRAM_BOT_TOKEN=___xxx___
DB_MONGODB_URI=___xxx___
DB_MONGODB_DATABASE=___xxx___
```

### Render

1. Deploy from public GutHub repository
2. Set up `/q/health` health endpoint

### Java code style

Java code style refers to the conventions and guidelines that developers follow when writing Java code to ensure
consistency and readability.

project: google-java-format,
link: https://github.com/google/google-java-format/blob/master/README.md#intellij-jre-config

### Gradle

#### Gradle Versions Plugin

Displays a report of the project dependencies that are up-to-date, exceed the latest version found, have upgrades, or
failed to be resolved:

command:

```
gradle dependencyUpdates
```

#### Gradle wrapper

The recommended way to execute any Gradle build is with the help of the Gradle Wrapper (referred to as "Wrapper")

```
./gradlew wrapper --gradle-version latest
```

#### Gradle ignore test

To skip any task from the Gradle build, we can use the -x or –exclude-task option. In this case, we’ll use “-x test” to
skip tests from the build.

To see it in action, let’s run the build command with -x option:

```
gradle build -x test
```

#### REST Endpoints

web app port configuration:

1. Docker file:

```
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT}"
```

2. Application properties

```
quarkus.http.port=${PORT:8080}
```

```
quarkus.http.root-path=/api
```

#### Health endpoint

Add the SmallRye Health extension:

```
    implementation 'io.quarkus:quarkus-smallrye-health'
```

Once added, the following endpoints become available automatically:

* GET /q/health → General health
* GET /q/health/live → Liveness check
* GET /q/health/ready → Readiness check

```
### BASE Configuration: General health
GET http://localhost:8080/q/health

### BASE Configuration: Liveness check
GET http://localhost:8080/q/health/live

### BASE Configuration: Readiness check
GET http://localhost:8080/q/health/ready
```

#### Customization:

By default, all Quarkus system endpoints are under /q/. To change the health check path:

```
quarkus.http.root-path=/api
quarkus.smallrye-health.root-path=/health
```
