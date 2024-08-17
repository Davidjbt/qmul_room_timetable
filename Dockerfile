# Stage 1: Build the application
FROM maven:3.8.6-openjdk-18 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

# Stage 2: Run the application with Selenium
FROM openjdk:17-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y wget unzip && \
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt install -y ./google-chrome-stable_current_amd64.deb && \
    rm google-chrome-stable_current_amd64.deb && \
    apt-get clean

COPY --from=build /app/target/qmul_room_timetable-0.0.1-SNAPSHOT.jar ./demo-qmul-room-timetable.jar
EXPOSE 8080
CMD ["java", "-jar", "demo-qmul-room-timetable.jar"]