FROM amazoncorretto:17-alpine
WORKDIR /app

# Copiar el archivo JAR y recursos generados
COPY Frontend/target/*.jar app.jar
COPY Comunidades_Agrupadas.json Comunidades_Agrupadas.json

EXPOSE 8081

# Configurar la aplicación para ejecutarse en modo producción
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
