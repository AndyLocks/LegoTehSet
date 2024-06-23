FROM maven as build

COPY . .

RUN mvn clean package 


FROM bellsoft/liberica-openjdk-debian:17

RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot

WORKDIR /app

COPY --from=build target target
COPY .env .env

ENTRYPOINT ["java", "-jar", "target/LegoTehSet-1.4.2.jar"]
