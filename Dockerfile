FROM maven:3.8-openjdk-17 AS build

WORKDIR /app

COPY cryptography/pom.xml .

RUN mvn dependency:go-offline

COPY cryptography/src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
