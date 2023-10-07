FROM maven:3.9.1-amazoncorretto-17 AS build
COPY . /tmp/
WORKDIR /tmp/

RUN mvn clean package -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /tmp/target/govlearn-api.jar /target/govlearn-api.jar
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=local", "-XX:MaxRAM=300m", "-XX:+UseSerialGC", "/target/govlearn-api.jar"]