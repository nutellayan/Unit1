FROM mysql
FROM openjdk:latest
COPY ./target/Unit1-0.1-alpha-2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "Unit1-0.1-alpha-2-jar-with-dependencies.jar"]