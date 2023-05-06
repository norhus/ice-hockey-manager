FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY /target/core-*.jar /app/core.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/core.jar"]
