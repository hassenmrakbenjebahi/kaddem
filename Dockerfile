FROM openjdk:17-jdk-alpine
LABEL authors="Hassen"
ADD target/kaddem-1.0.0.jar kaddem-1.0.0.jar
ENTRYPOINT ["java","-jar","/kaddem-1.0.0.jar"]