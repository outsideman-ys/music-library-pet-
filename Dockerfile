FROM eclipse-temurin:17-jdk

WORKDIR /game

COPY target/game-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]