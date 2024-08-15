# Stage 1: Build the application
FROM maven:3.8.6-openjdk-18 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Stage 2: Run the application
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=build /app/target/qmul_room_timetable-0.0.1-SNAPSHOT.jar ./demo-qmul-room-timetable.jar
EXPOSE 8080
CMD ["java", "-jar", "demo-qmul-room-timetable.jar"]