#!/bin/sh
echo "Attendo che il database MySQL sia pronto..."

while ! nc -z antifurto-db 3306; do
	echo "MySQL non ancora disponibile, attendo..."
	sleep 2
done

echo "MySQL è pronto! Avvio l'applicazione..."
exec java -jar app.jar
