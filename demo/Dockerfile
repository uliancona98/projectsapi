FROM openjdk:8-jdk-alpine
LABEL maintainer="uliancona@hotmail.com"
VOLUME /main-app
ADD target/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar","/app.jar"]