# Usando imagem oficial do OpenJDK
FROM openjdk:17-jdk-alpine

# Define diretório de trabalho
WORKDIR /app

# Copia o jar construído para o container
COPY target/ce-sage-catalog-0.0.4.jar app.jar

# Expõe a porta que sua aplicação roda (exemplo 8080)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]

