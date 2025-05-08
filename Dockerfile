FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/WebRiseTask-0.0.1-SNAPSHOT.jar app.jar
CMD java -jar app.jar --spring.profiles.active=docker