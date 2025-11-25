# Imagem base com JDK 17
FROM eclipse-temurin:17-jdk-alpine

RUN apk add --no-cache bash

# Criar diretório de trabalho
WORKDIR /app

# Copiar o fat JAR gerado pelo Maven
COPY target/clientejava-1.0.0.jar app.jar

# Copiar o script wait-for-it.sh
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
