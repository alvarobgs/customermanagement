FROM maven:3.9.2-amazoncorretto-17 as build

WORKDIR /app
COPY ./ .
RUN mvn clean install -DskipTests
RUN mv /app/target/*.jar app.jar

FROM amazoncorretto:17-alpine3.17 as base

RUN apk upgrade

COPY --from=build /app/app.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]