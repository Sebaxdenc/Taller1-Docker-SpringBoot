# Usar imagen base de OpenJDK 21
FROM eclipse-temurin:21-jre-alpine

# Información del mantenedor
LABEL maintainer="sebastian-medina"
LABEL description="Aplicación de gestión de árbitros"
LABEL version="1.0.0"

# Instalar dependencias necesarias
RUN apk add --no-cache curl

# Crear directorio de la aplicación y datos
WORKDIR /app
RUN mkdir -p /app/data

# Copiar el archivo JAR de la aplicación
# Spring Boot genera un JAR ejecutable en target/
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Crear usuario no-root para seguridad
RUN addgroup -g 1001 -S spring && \
    adduser -u 1001 -S spring -G spring

# Cambiar propietario de archivos
RUN chown -R spring:spring /app
USER spring:spring

# Exponer el puerto donde corre la aplicación
EXPOSE 8080

# Variables de entorno opcionales
ENV JAVA_OPTS=""
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]