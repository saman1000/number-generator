FROM maven:3.9.16-amazoncorretto-25-alpine AS BUILD

WORKDIR /app

COPY . .

RUN mvn package

FROM amazoncorretto:25-alpine3.24-jdk AS REST

WORKDIR /app

COPY --from=BUILD /app/rest-server/target/rest-server-1.1-SNAPSHOT.jar ./

EXPOSE 10001

ENTRYPOINT ["java", "-Dspring.profiles.active=lottery,rest", "-jar", "./rest-server-1.1-SNAPSHOT.jar"]