#
# Build stage
#
FROM maven:3.9.1-amazoncorretto-19 AS build
RUN mkdir -p /usr/device
WORKDIR /usr/device
ADD . /usr/device
RUN mvn -f /usr/device/pom.xml clean package -Dmaven.test.skip

#
# Package stage
#
FROM openjdk:19
ARG JAR_FILE=/usr/device/target/*.jar
COPY --from=build $JAR_FILE /device/runner3.jar
EXPOSE 8080
ENTRYPOINT java -jar /device/runner3.jar