FROM openjdk:17-alpine
WORKDIR /app

COPY target/riot-api-spring-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/static ./resources/

EXPOSE 90

CMD ["java", "-jar", "app.jar"]