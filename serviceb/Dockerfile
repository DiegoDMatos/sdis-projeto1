FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/serviceb-1.0.0.jar app.jar

# Copiar o script wait-for-it.sh
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
