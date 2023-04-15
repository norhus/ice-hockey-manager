FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY /target/game-scheduler-*.jar /app/game-scheduler.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/game-scheduler.jar"]