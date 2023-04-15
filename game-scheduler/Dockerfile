FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY /src/main/resources/application.properties /app/resources/
COPY /target/game-scheduler-*.jar /app/game-scheduler.jar

EXPOSE 8083

ENV SPRING_CONFIG_LOCATION=/app/resources/application.properties

ENTRYPOINT ["java", "-jar", "/app/game-scheduler.jar"]