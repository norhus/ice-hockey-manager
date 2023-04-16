FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY /target/league-table-*.jar /app/league-table.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app/league-table.jar"]