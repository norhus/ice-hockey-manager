FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY /target/core-*.jar /app/core.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/core.jar"]