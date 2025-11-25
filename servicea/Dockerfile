FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/servicea-1.0.0.jar app.jar

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
