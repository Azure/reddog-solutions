# multi-stage build using Microsoft OpenJDK
FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

# Using MS Build of OpenJDK image
FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
# Setting the working directory
WORKDIR /opt/app
# Exposing the port
EXPOSE 8084
# Copying the jar file from builder image
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
# Running the jar file
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar" ]