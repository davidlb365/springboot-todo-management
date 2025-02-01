FROM maven:3.8.6-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18
COPY target/todo-management-0.0.1-SNAPSHOT.jar /app/todo-management.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/todo-management.jar"]