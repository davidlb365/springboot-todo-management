FROM openjdk:18

WORKDIR /app

COPY target/todo-management-0.0.1-SNAPSHOT.jar /app/todo-management.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/todo-management.jar"]