# Etapa 1: construir la app con Gradle y Java 17
FROM gradle:7.6.1-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

# Etapa 2: imagen liviana para ejecutar la app
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/nao-market-1.0.jar app.jar

EXPOSE 8090

ENTRYPOINT ["java", "-jar", "app.jar"]