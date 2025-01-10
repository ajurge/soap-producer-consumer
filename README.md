# Build and Run

- Java 23
- docker

Set the following environmental variables:
```
DOCKER_USERNAME=dockerhub-usr
DOCKER_PASSWORD=dockerhub-pwd
```

## Build

First build and run the soap-producer: `./gradlew :soap-producer:bootRun`

`./gradlew bootBuildImage`

Stop the `./gradlew :soap-producer:bootRun`

## Run

`docker-compose --env-file docker.env up`

Swagger UI: http://localhost:8080/swagger-ui.html

