#  Proyecto Demo CI Java

Repositorio desarrollado como parte de la **Actividad 1 - TA_4** para la asignatura de Automatización de Pruebas.  

El objetivo es construir un proyecto Java con **Maven**, y un **pipeline de Integración Continua (CI)** 

---

##  Objetivos

1. Configurar un proyecto Maven para gestionar dependencias y ejecutar pruebas automatizadas.  
2. Implementar pruebas unitarias atómicas e independientes.  
3. Configurar un pipeline CI que compile, ejecute los tests y genere reportes accesibles.  
4. Documentar y versionar cada paso del proceso.

---

##  Estructura del Proyecto

demo-ci-java/
├── pom.xml # Archivo de configuración Maven
├── .github/workflows/ci.yml # Pipeline CI (GitHub Actions)
├── .gitignore # Exclusiones de Git
├── README.md # Documentación del proyecto
├── src/
│ ├── main/java/com/demo/Calculadora.java # Código principal
│ └── test/java/com/demo/CalculadoraTest.java # Pruebas unitarias
└── target/ # Carpeta generada por Maven (excluida en Git)

## Comandos utilizados

## Ejecución de pruebas
mvn clean test

## Generación de reporte HTML
mvn surefire-report:report-only

## Validar versión de Maven y Java
mvn -v
java -version

## Pipeline CI
Archivo: .github/workflows/ci.yml

## El pipeline se ejecuta automáticamente en cada push o pull request, y realiza:

Configuración de Java 17

Compilación y ejecución de los tests (mvn clean test)

Publicación de reportes XML y HTML generados por Surefire

## El archivo completo:

name: CI (Maven)
on:
  push:
  pull_request:
jobs:
  build-test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          distribution: 'temurin'
          java-version: '17'
          cache: 'maven'
      - name: Build & Test
        run: mvn -B clean test
      - name: Upload Surefire reports
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: surefire-reports
          path: target/surefire-reports
      - name: Generate HTML test report
        run: mvn surefire-report:report-only
      - name: Upload HTML report
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: html-test-report
          path: target/site/surefire-report.html

## Reportes

XML/Text: generados en target/surefire-reports/
HTML: generado en target/site/surefire-report.html
y publicado como artefacto en GitHub Actions (html-test-report)

## Resultados finales

Proyecto Maven funcional con JUnit 5

Pipeline CI activo con ejecución automática de tests

Reportes navegables y descargables desde GitHub Actions

Código versionado con buenas prácticas de Git

## Autores

Franco Gatica Salinas
Proyecto desarrollado como parte de la Actividad 1 – TA_4: Automatización de Pruebas
Docente: MAURICIO IGNACIO SAAVEDRA HASSAN