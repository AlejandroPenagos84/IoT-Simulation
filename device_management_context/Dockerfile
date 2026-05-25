# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app

# 1. Copiar primero el Wrapper de Gradle (Rara vez cambia)
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew

# 2. Copiar los archivos de configuración donde defines tus librerías y dependencias
COPY build.gradle settings.gradle ./
# Si en tu proyecto manejas el catálogo de versiones (libs.versions.toml), descomenta la línea de abajo:
# COPY gradle/libs.versions.toml ./gradle/

# 3. Descargar y cachear las dependencias reales del proyecto antes de mirar el código fuente
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew dependencies --no-daemon || true

# 4. Copiar el código fuente. 
# Si cambias una clase, un controlador o una interfaz, Docker saltará directo aquí, REUSANDO las capas 1, 2 y 3.
COPY src ./src

# 5. Compilar el archivo JAR de Spring Boot usando la caché acumulada
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon bootJar -x test

# --- Etapa de ejecución (Runtime ultra ligera) ---
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copiar el archivo empaquetado desde la etapa de construcción
COPY --from=builder /app/build/libs/*.jar app.jar

# Mantenemos el puerto 8081 que definiste en este último archivo
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]