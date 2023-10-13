FROM eclipse-temurin:17
ARG JAR_FILE=target/*.jar
COPY ./target/pessoas-0.0.1-SNAPSHOT.jar /kaniko/buildcontext/
ENTRYPOINT ["java", "-jar", "/kaniko/buildcontext/pessoas-0.0.1-SNAPSHOT.jar"]