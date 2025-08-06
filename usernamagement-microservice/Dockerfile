FROM maven:3.9.1-amazoncorretto-19 AS build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:19
WORKDIR /app
COPY --from=build /home/app/target/usernamagement-microservice-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
CMD ["java", "-jar", "/usr/local/lib/demo.jar"]