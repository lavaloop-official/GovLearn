### Willkommen im Projekt GovLearn!

## Docker Guide
Um das Projekt GovLearn möglichst einfach auf verschiedenen Betriebssystemen zu entwickeln,
wird Docker verwendet. Installiere die Docker Engine zunächst mit den folgenden Schritten: 
https://docs.docker.com/engine/install/

Falls du ein anderes Betriebssystem als Windows verwendest, musst du möglicherweise Docker Compose separat installieren https://docs.docker.com/compose/install/ .
Dies ist eine Voraussetzung, um die Datenbank zu starten.

## PostgreSQL - Datenbank Guide
Die Datenbank lässt sich ganz einfach mit Docker starten. 
Öffne ein Terminal deiner Wahl und navigiere in das root Verzeichnis von GovLearn.
Führe hier das Kommando `docker-compose up` aus. PostgreSQL wird gestartet und ist unter dem Port 5432 erreichbar.
Anmeldedaten:
User: `postgresUser`
Passwort: `postgresPassword`
Datenbank: `govlearn`;
Daten werden persistent lokal im Ordner postgres-data gespeichert.
Sollte dieser bei dir fehlen, erstelle ihn im root Verzeichnis des Projekts.
Um die Datenbank zurückzusetzen, kannst du alle Inhalte aus dem Ordner löschen.

## React - Frontend Guide
Um mit React zu arbeiten, musst du die Node.js Runtime installieren. Sie ermöglicht das Ausführen von JavaScript.
Lade sie hier herunter und installiere sie: https://nodejs.org/en/download .
Wenn du dich mit JavaScript Entwicklung bereits auskennst, kannst du auch den Node Version Manager (nvm) verwenden.

Nun bist du bereit mit der Frontendentwicklung zu beginnen. Starte ein Terminal und navigiere in den govlearn-client Ordner.
Hier musst du vor dem ersten Start des Frontends zunächst `npm install` ausführen, um die Abhängigkeiten der App zu installieren.
Nun kannst du React mit `npm run dev` starten. Wechsel in deinen Browser und gebe in der URL Zeile `localhost` ein.

## Backend Guide
Deine Meinung ist gefragt, es besteht die Möglichkeit Flask (Python), Spring Boot (Java) oder Express (JavaScript) zu verwenden.

## Deployment
Mit Docker kannst du das Frontend bereits deployen. Dazu musst du wieder mit dem Terminal nach govlearn-client
navigieren und dort `docker build -t govlearn-frontend .` ausführen. 
Du hast nun einen Docker Container für das Frontend erstellt (im Container läuft der Webserver Nginx).
Um den Container zu starten, verwende den Befehl `docker run -p 80:80 govlearn-frontend`.
Damit ist das Frontend wie in der Entwicklung im Browser unter `localhost` erreichbar.
Achte darauf, dass das Frontend nicht bereits gestartet ist oder ein anderes Program auf Port 80 läuft.

Viel Erfolg & melde dich gerne bei Fragen bei mir.

Jan 