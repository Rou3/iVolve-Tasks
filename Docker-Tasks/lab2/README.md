# Lab 2: Building and Packaging Java Applications with Maven

This lab demonstrates building, testing, and running a Java application using Maven.

## Prerequisites

- Java JDK installed
- Maven installed

## Clone the Application

```bash
git clone https://github.com/Ibrahim-Adel15/build2.git
cd build2
````

## Run Unit Tests

```bash
mvn test
```

Check test results in:

```bash
target/surefire-reports/
```

## Build the Application

```bash
mvn package
```

The generated artifact will be located at:

```bash
target/hello-ivolve-1.0-SNAPSHOT.jar
```

## Run the Application

```bash
java -jar target/hello-ivolve-1.0-SNAPSHOT.jar
```

## Verify Application

* Ensure the app runs without errors.
* Check expected output on the console.
