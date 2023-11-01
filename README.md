### Willkommen im Projekt GovLearn!

## IDE
Wusstest du, dass du Intellij und alle JetBrain Produkte als Student kostenlos nutzen kannst?
Mehr Informationen hier: https://www.jetbrains.com/de-de/lp/cpp-leaflet/students/ <br>
Eine Alternative bietet Visual Studio Code: https://code.visualstudio.com/

## Docker Guide
Um das Projekt GovLearn möglichst einfach auf verschiedenen Betriebssystemen zu entwickeln,
wird Docker verwendet. Installiere die Docker Engine zunächst mit den folgenden Schritten: 
https://docs.docker.com/engine/install/

Falls du ein anderes Betriebssystem als Windows verwendest, musst du möglicherweise Docker Compose separat installieren https://docs.docker.com/compose/install/ .
Dies ist eine Voraussetzung, um die Datenbank zu starten.

## PostgreSQL - Datenbank Guide
Die Datenbank lässt sich ganz einfach mit Docker starten. 
Öffne ein Terminal deiner Wahl und navigiere in das Verzeichnis govlearn-api.
Führe hier das Kommando `docker-compose up` aus. PostgreSQL wird gestartet und ist unter dem Port 5432 erreichbar.
Anmeldedaten:<br>
User: `postgresUser`<br>
Passwort: `postgresPassword`<br>
Datenbank: `govlearn`<br>
Daten werden persistent lokal im Ordner postgres-data gespeichert.
Sollte dieser bei dir fehlen, erstelle ihn im root Verzeichnis des Projekts.
Um die Datenbank zurückzusetzen, kannst du alle Inhalte aus dem Ordner löschen.

## React - Frontend Guide
Um mit React zu arbeiten, musst du die Node.js Runtime installieren. Sie ermöglicht das Ausführen von JavaScript.
Lade sie hier herunter und installiere sie: https://nodejs.org/en/download.
Wenn du dich mit JavaScript Entwicklung bereits auskennst, kannst du auch den Node Version Manager (nvm) verwenden.

Nun bist du bereit mit der Frontendentwicklung zu beginnen. Starte ein Terminal und navigiere in den govlearn-client Ordner.
Hier musst du vor dem ersten Start des Frontends zunächst `npm install` ausführen, um die Abhängigkeiten der App zu installieren.
Nun kannst du React mit `npm run dev` starten. Wechsel in deinen Browser und gebe in der URL Zeile http://localhost ein.

## Spring Boot - Backend Guide
Damit du das Backend starten kannst, musst du erstmal Java 17 installieren. Eine Erklärung findest du hier https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html#GUID-BCE568C9-93D3-49F4-9B0C-9DD4A3419792, wie auch in diversen YouTube Anleitungen.
Lade zunächst alle Maven Abhängigkeiten herunter. Je nach IDE funktioniert es anders. Starte nun die Datenbank mit Docker, da Spring Boot ohne sie nicht lauffähig ist.
Öffne den Ordner govlearn-api und navigiere zu GovlearnApiApplication.java. Hier kannst du in Intellij auf ausführen klicken. Die Swagger Dokumentation findest du im Browser unter http://localhost:8080/swagger-ui.html.

## Deployment
Mit Docker kannst du das Frontend und Backend bereits deployen. Dazu musst du wieder mit dem Terminal nach govlearn-client
navigieren und dort `docker build -t govlearn-frontend .` ausführen.
Navigiere ins Backend und führe dort `docker build -t govlearn-backend .` aus.
Du hast nun einen Docker Container für das Frontend erstellt (im Container läuft der Webserver Nginx).
Um die Container zu starten, gehe wieder ins root Verzeichnis und verwende den Befehl `docker compose -f compose-local.yaml up`.
Damit sind das Frontend, Backend und Datenbank weiterhin unter den gleichen URLs wie in der Entwicklung erreichbar.
Achte darauf, dass das keine anderen Programme auf den Ports 80, 8080 und 5432 laufen.

Viel Erfolg & melde dich gerne bei Fragen bei mir.

Jan

push to main branch without request, test