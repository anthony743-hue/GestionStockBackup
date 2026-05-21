#!/bin/bash

BUILD_DIR="out"
LIB_DIR="lib"

echo "Compilation des sources Java..."
# Classpath uniquement avec servlet-api et les librairies de l'application
find -name "*.java" > sources.txt
javac -cp "$LIB_DIR/postgresql-42.7.3.jar" -d $BUILD_DIR @sources.txt

if [ $? -ne 0 ]; then
    echo "ERREUR: La compilation a échoué."
    exit 1
fi

java -cp "$LIB_DIR/postgresql-42.7.3.jar:$BUILD_DIR" Main

if [ $? -ne 0 ]; then
    echo "ERREUR: L'execution a échoué."
    exit 1
fi