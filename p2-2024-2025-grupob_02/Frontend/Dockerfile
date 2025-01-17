FROM amazoncorretto:17-alpine
WORKDIR /app

# Copiar el archivo JAR y recursos generados
COPY Frontend/target/*.jar app.jar
COPY Comunidades_Agrupadas.json Comunidades_Agrupadas.json
#Cambiarlo con el JSON que me de

EXPOSE 8081

# Configurar la aplicación para ejecutarse en modo producción
CMD ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
