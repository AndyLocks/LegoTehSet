FROM openjdk:23

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

COPY src ./src

ENTRYPOINT ["./mvnw", "spring-boot:run"]