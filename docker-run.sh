#!/bin/bash

# Script simple para construir y ejecutar la aplicación con Docker
echo "🚀 Construyendo aplicación de árbitros..."

# Construir el JAR con Maven
echo "📦 Compilando con Maven..."
./mvnw clean package -DskipTests

# Construir imagen Docker
echo "🐳 Construyendo imagen Docker..."
docker compose build

# Ejecutar aplicación
echo "▶️  Iniciando aplicación..."
docker compose up -d

echo "✅ Aplicación disponible en: http://localhost:8080"
echo "📋 Ver logs: docker compose logs -f"
echo "🛑 Detener: docker compose down"
