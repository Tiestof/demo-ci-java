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

### Ejecución de pruebas
mvn clean test

### Generación de reporte HTML
mvn surefire-report:report-only

### Validar versión de Maven y Java
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


# Actividad 2 – Pruebas Funcionales con Cucumber (BDD)

En esta segunda parte se amplió el proyecto de la Actividad 1 para incluir pruebas funcionales usando el enfoque BDD (Behavior Driven Development) con Cucumber + JUnit 4.
El objetivo es validar comportamientos completos del sistema (por ejemplo, el flujo de login), además de las pruebas unitarias ya implementadas previamente.

## Nueva estructura del proyecto

demo-ci-java
│   .gitignore
│   CONTRIBUTING.md
│   pom.xml
│   README.md
│   TODO.md
│
├───.github
│   └───workflows
│           ci.yml
│
├───.vscode
│       settings.json
│
├───src
│   ├───main/java/com/demo
│   │       App.java
│   │       Calculadora.java
│   │
│   └───test/java/com/demo
│       │   CalculadoraTest.java
│       │   PrimerTest.java
│       │   RunCucumberTest.java
│       │
│       ├───helpers
│       │       AuthHelper.java
│       │
│       └───steps
│               LoginSteps.java
│
└───src/test/resources
    │   junit-platform.properties
    │
    └───com/demo/features
            login.feature

## Archivos creados y modificados

### Archivos nuevos
| Archivo                     | Ubicación                               | Descripción                                                                   |
| --------------------------- | --------------------------------------- | ----------------------------------------------------------------------------- |
| `login.feature`             | `src/test/resources/com/demo/features/` | Define los escenarios BDD para el flujo de login (casos válidos e inválidos). |
| `LoginSteps.java`           | `src/test/java/com/demo/steps/`         | Implementa los pasos (`Given`, `When`, `Then`) definidos en `login.feature`.  |
| `AuthHelper.java`           | `src/test/java/com/demo/helpers/`       | Clase auxiliar que simula la autenticación de usuario.                        |
| `RunCucumberTest.java`      | `src/test/java/com/demo/`               | Clase *runner* que ejecuta los escenarios de Cucumber mediante JUnit 4.       |
| `junit-platform.properties` | `src/test/resources/`                   | Configura parámetros de ejecución y reportes de Cucumber.                     |

### Archivos modificados

| Archivo     | Cambio realizado                                                            |
| ----------- | --------------------------------------------------------------------------- |
| `pom.xml`   | Se agregaron dependencias y configuración para integrar Cucumber y JUnit 4. |
| `README.md` | Se actualizó con documentación de la Actividad 2.                           |

## Cambios en el pom.xml respecto a la Actividad 1

| **Componente**                       | **Descripción del cambio**                                                                                 | **Propósito**                                           |
| ------------------------------------ | ---------------------------------------------------------------------------------------------------------- | ------------------------------------------------------- |
| **Dependencias Cucumber**            | Se agregaron:<br>• `cucumber-java`<br>• `cucumber-junit`<br>• `cucumber-core` (v7.14.0)                    | Permitir escribir y ejecutar pruebas BDD con Cucumber.  |
| **JUnit 4**                          | Se añadió `junit:4.13.2`.                                                                                  | Requerido por el runner `@RunWith(Cucumber.class)`.     |
| **Provider de ejecución (Surefire)** | Se fijó `maven-surefire-plugin` en versión **2.22.2** y se agregó `surefire-junit4`.                       | Asegurar compatibilidad con Cucumber (que usa JUnit 4). |
| **Configuración del plugin**         | Se incluyó:<br>• `**/RunCucumberTest.java`<br>• `cucumber.plugin=pretty, html:target/cucumber-report.html` | Ejecutar solo los tests BDD y generar reporte HTML.     |
| **Ejecución separada**               | Los tests unitarios (Actividad 1) y los BDD (Actividad 2) se ejecutan de forma independiente.              | Mantener independencia entre niveles de prueba.         |



## Resumen

Las pruebas unitarias de la Actividad 1 se mantienen sin cambios (JUnit 5).

Se añadieron pruebas funcionales BDD que describen escenarios de negocio.

El proyecto ahora integra Cucumber + JUnit 4 junto con JUnit 5, permitiendo ambos estilos de prueba.

Los reportes BDD se generan automáticamente en: target/cucumber-report.html

## Ejemplo de ejecución

mvn clean test

### Ejemplo de salida esperada en consola:

Scenario: Login exitoso con credenciales válidas
  Given que el usuario está en la pantalla de login
  When ingresa usuario "admin" y contraseña "1234"
  Then el sistema muestra el mensaje "Bienvenido admin"

Scenario Outline: Login fallido con credenciales inválidas o vacías
  ...
BUILD SUCCESS
Report generated: target/cucumber-report.html

## Autores

Franco Gatica Salinas
Proyecto desarrollado como parte de la Actividad 1 – TA_4: Automatización de Pruebas
Docente: MAURICIO IGNACIO SAAVEDRA HASSAN