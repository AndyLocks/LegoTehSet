FROM openjdk:23

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY src ./src
COPY .env .env

ENTRYPOINT ["./mvnw", "spring-boot:run"]