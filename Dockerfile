FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build target/todo-management-0.0.1-SNAPSHOT.jar /app/todo-management.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/todo-management.jar"]