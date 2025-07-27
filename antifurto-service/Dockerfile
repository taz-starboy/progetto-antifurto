# 1. Usa una base image Java leggera
# Usa una Java 17 JDK già pronta, versione Alpine, cioè molto leggera.
# eclipse-temurin è un'immagine Docker ufficiale e stabile per Java.
FROM eclipse-temurin:17-jdk-alpine

# 6. Installa netcat per l'attesa del DB
RUN apk add --no-cache netcat-openbsd

# 2. Imposta la working directory nel container
# Imposta la cartella di lavoro nel container.
# Tutti i comandi successivi (es. COPY, RUN, ecc.) saranno relativi a questa directory.
WORKDIR /app

# 3. Copia il file JAR nel container
# Copia il .jar dentro il container e lo rinomina in app.jar per semplicità.
COPY target/antifurto-service-0.0.1-SNAPSHOT.jar app.jar

# 4. Dichiara che il container userà la porta 8080, la porta standard di Spring Boot.
# Non apre davvero la porta, ma serve per chiarezza e strumenti come Docker Compose
EXPOSE 8080

# 5. Comando per avviare l'applicazione Spring Boot
# Comando che viene eseguito quando avvii il container. 
# In questo caso, lancia java -jar app.jar per avviare la tua app Spring Boot.
ENTRYPOINT ["java", "-jar", "app.jar"]

