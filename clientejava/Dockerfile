FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY target/clientejava-1.0.0.jar app.jar

COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

ENTRYPOINT ["java", "-jar", "app.jar"]
