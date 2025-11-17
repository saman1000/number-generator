FROM maven:3.9.11-amazoncorretto-21-alpine AS BUILD

WORKDIR /app

COPY . .

RUN mvn package

FROM amazoncorretto:21-alpine3.21-jdk AS REST

WORKDIR /app

COPY --from=BUILD /app/rest-server/files ./files
COPY --from=BUILD /app/rest-server/target/rest-server-1.1-SNAPSHOT.jar ./

EXPOSE 10001

# Command to run Uvicorn, serving your FastAPI app
#ENTRYPOINT ["sleep", "500"]
ENTRYPOINT ["java", "-Dspring.profiles.active=lottery,rest", "-jar", "./rest-server-1.1-SNAPSHOT.jar"]