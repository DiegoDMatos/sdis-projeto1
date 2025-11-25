# Use JDK 17
FROM eclipse-temurin:17-jdk

# Define diretório de trabalho
WORKDIR /app

# Copia o JAR gerado pelo Maven
COPY target/servicea-1.0.0.jar app.jar

# Copiar o script wait-for-it.sh
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

# Expõe porta (se o serviço tiver API)
EXPOSE 8081

# Comando para rodar o microserviço
ENTRYPOINT ["java", "-jar", "app.jar"]
