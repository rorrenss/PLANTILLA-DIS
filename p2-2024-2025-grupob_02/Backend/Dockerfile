FROM amazoncorretto:17-alpine

WORKDIR /app
COPY ./Backend/target/*.jar app.jar
COPY ./TurismoComunidades.json TurismoComunidades.json
COPY ./Comunidades_Agrupadas.json Comunidades_Agrupadas.json

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]