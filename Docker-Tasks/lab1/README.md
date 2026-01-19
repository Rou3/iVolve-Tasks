# Lab 1: Building and Packaging Java Applications with Gradle

This lab demonstrates building, testing, and running a Java application using Gradle.

## Prerequisites

- Java JDK installed
- Gradle installed

## Clone the Application

```bash
git clone https://github.com/Ibrahim-Adel15/build1.git
cd build1
````

## Run Unit Tests

```bash
gradle test
```

Check the test results in:

```bash
build/reports/tests/test/index.html
```

## Build the Application

```bash
gradle build
```

The generated artifact will be located at:

```bash
build/libs/ivolve-app.jar
```

## Run the Application

```bash
java -jar build/libs/ivolve-app.jar
```

## Verify Application

* Ensure the app runs without errors.
* Check expected output on the console.


