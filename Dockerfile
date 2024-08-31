FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
COPY uploads-dev/reportpictures/sampleReport.png /uploads-dev/reportpictures/sampleReport.png
ENTRYPOINT ["java","-jar","/app.jar"]
