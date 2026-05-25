# syntax=docker/dockerfile:1.7
FROM eclipse-temurin:25-jdk AS builder
WORKDIR /app

# 1. Copiar primero el Wrapper de Gradle
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew

# 2. Copiar las definiciones de dependencias
COPY build.gradle settings.gradle ./
# Si usas el catálogo de versiones de Gradle (libs.versions.toml), descomenta la línea de abajo:
# COPY gradle/libs.versions.toml ./gradle/

# 3. Descargar y cachear las dependencias ANTES de copiar el código fuente
# Usamos '--mount=type=cache' para reusar los archivos descargados localmente
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew dependencies --no-daemon || true

# 4. Ahora sí, copiamos el código fuente (Si el código cambia, Docker arranca desde aquí)
COPY src ./src

# 5. Compilar el JAR usando la caché de dependencias previamente armada
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew --no-daemon bootJar -x test

# --- Etapa de ejecución (Runtime) ---
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copiar el JAR generado en la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

# Es buena práctica apuntar a 'app.jar' directamente ya que estás en el WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]