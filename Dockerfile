FROM openjdk:17

EXPOSE 8080

COPY target/Diploma-1.0-SNAPSHOT.jar diploma_back.jar

CMD ["java", "-jar", "diploma_back.jar"]