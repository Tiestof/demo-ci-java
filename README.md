# Proyecto Demo CI Java  
**Autor:** Franco Gatica Salinas  
**Asignatura:** Automatización de Pruebas – Unidad II  
**Institución:** IPLACEX – Escuela de Informática y Telecomunicaciones  
**Docente:** Mauricio Ignacio Saavedra Hassan  
**Versión del proyecto:** 2.0 (Integración Continua + BDD + Performance)  
**Fecha de entrega:** Noviembre 2025  

---

### Descripción General

Este repositorio contiene el desarrollo completo de las **Actividades 1 y 2** del Taller TA_4, correspondientes a la **Unidad II** de la asignatura *Automatización de Pruebas*.  
El objetivo principal es implementar un flujo de **Integración Continua (CI)** profesional para un proyecto Java, incorporando:

- Gestión de versiones con **Git y GitHub**.  
- Construcción y pruebas automatizadas con **Maven y JUnit 5**.  
- Escenarios funcionales BDD con **Cucumber + JUnit 4**.  
- Pruebas de rendimiento con **k6**.  
- Generación automática de **reportes, métricas y alertas** dentro del pipeline de CI.  

El proyecto refleja la aplicación práctica de los contenidos abordados en los materiales ME_1 a ME_4 y sigue las pautas establecidas en el taller TA_4.

---



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

## Integración de métricas de pruebas (simulación de dashboard en CI)

Para esta etapa se amplió el pipeline de CI (.github/workflows/ci.yml) con nuevos jobs que muestran resultados de pruebas funcionales (BDD) y de rendimiento (k6) directamente dentro de GitHub Actions, sin necesidad de usar herramientas externas como Grafana o Prometheus.

### ¿Qué se agregó?

#### Job bdd-tests (Cucumber)

Ejecuta los escenarios BDD definidos en login.feature.

Genera dos reportes:

target/cucumber-report.html → reporte navegable.

target/cucumber.json → usado para extraer métricas.

Usa la herramienta jq para leer el archivo JSON y calcula:

Total de escenarios.

Escenarios pasados y fallidos.

Muestra un resumen con métricas en el panel Job Summary de GitHub Actions (por ejemplo: “5 escenarios, 0 fallidos”).

Esto simula un dashboard funcional, mostrando los resultados BDD directamente en el pipeline.

#### Job perf-k6 (k6 Performance Test)

Ejecuta el script tests/performance/login_performance_test.js, que simula 5 usuarios concurrentes por 10 segundos contra el endpoint público https://test.k6.io/.

Exporta un archivo target/perf/summary.json con todas las métricas del test.

Extrae con jq los valores clave:

Throughput (req/s)

Latencia p95 (ms)

Error rate (%)

Los publica como un resumen visual en el Job Summary de Actions (tal como se ve en la imagen).

Además, guarda el archivo summary.json como artefacto descargable para análisis posterior.

Esto representa un mini-dashboard de rendimiento generado automáticamente dentro del pipeline.

#### ejemplo de salida esperada

Job Summary de GitHub Actions:

  Throughput (req/s): ~9.5
  Latencia p95: ~24.7 ms
  Error rate: 0

## Tecnologías utilizadas

GitHub Actions → motor de CI y visualización.

Apache Maven → ejecución de pruebas unitarias y BDD.

k6 → pruebas de carga y performance.

jq → análisis de archivos JSON para extraer métricas.


Configuración de alertas automáticas ante fallos o degradaciones

En esta sección se explica cómo se configuraron y cómo se pueden ampliar las alertas automáticas del pipeline de Integración Continua (CI) para detectar errores o degradaciones tanto en pruebas funcionales (unitarias y BDD) como en pruebas de rendimiento (k6).

### Alertas nativas de GitHub Actions

GitHub Actions envía notificaciones automáticas cuando una ejecución del workflow falla o cambia de estado.
Estas alertas se pueden recibir por:

Correo electrónico (activado por defecto en tu cuenta de GitHub).

Notificaciones web o móviles dentro de la interfaz de GitHub.

Integraciones externas (Slack, Teams, Discord, etc.).

### Configuración básica

Ir a Settings → Notifications en el perfil de GitHub.

Activar:

“Email” → para recibir alertas por fallos de ejecución.

“Web and mobile” → para notificaciones dentro de la plataforma.

Cualquier error en el pipeline (por ejemplo, una prueba fallida o umbral incumplido) generará automáticamente una alerta.

### Alertas por degradación de rendimiento (k6)

En el archivo login_performance_test.js se definieron umbrales

  http_req_failed: ['rate<0.01'],   // Menos de 1% de errores permitidos
  http_req_duration: ['p(95)<500'], // 95% de las solicitudes debajo de 500 ms

Si alguno de estos umbrales se incumple:

k6 devuelve un código de error 99.

GitHub Actions marca el job como “Failed”.

Se dispara automáticamente una alerta por correo y se visualiza en el panel de Actions.

### Alertas por fallos funcionales (BDD y unitarias)

Cuando una prueba unitaria o un escenario BDD falla:

Maven devuelve código de error 1.

GitHub Actions marca el job correspondiente como “Failed”.

Automáticamente se envía un correo o notificación al responsable del repositorio.

### Resultado esperado

Cualquier fallo o degradación marca el pipeline en rojo.

GitHub Actions notifica automáticamente por correo o integración externa.

El equipo puede revisar el detalle en el log o en el resumen del Job.

# Autores

Franco Gatica Salinas
Proyecto desarrollado como parte de la Actividad 1 – TA_4: Automatización de Pruebas
Docente: MAURICIO IGNACIO SAAVEDRA HASSAN