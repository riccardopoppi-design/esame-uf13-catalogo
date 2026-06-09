# ==========================================
# STAGE 1: Build (Immagine pesante con JDK e Maven)
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia il file di configurazione Maven
COPY pom.xml .

# Ottimizzazione cache: scarica le dipendenze in un layer isolato
RUN mvn dependency:go-offline

# Copia i sorgenti del progetto
COPY src ./src

# Compila e genera il pacchetto .jar saltando i test per velocizzare la build del container
RUN mvn clean package -DskipTests

# ==========================================
# STAGE 2: Run (Immagine minimale con JRE Alpine)
# ==========================================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Crea la cartella destinata a ospitare i log rotanti sull'host
RUN mkdir logs

# Copia il file .jar compitilato dallo stage precedente (rinominandolo per comodità)
COPY --from=build /app/target/catalogo-cap-0.0.1-SNAPSHOT.jar app.jar

# Espone la porta standard di Spring Boot
EXPOSE 8080

# Avvia l'applicazione iniettando programmaticamente il profilo "prod"
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]