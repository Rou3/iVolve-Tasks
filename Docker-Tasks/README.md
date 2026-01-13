# Lab 1: Java Application with Gradle

This lab uses Java 17 and Gradle for managing dependencies and building the project. The archiveBaseName in build.gradle defines the name of the generated JAR file.

This lab demonstrates how to build, test, and run a simple Java application using **Gradle

1. Clone the project:
   ```bash
   -git clone https://github.com/Ibrahim-Adel15/build1.git
   -cd build1

2. Install Gradle (if not installed):
   -sudo apt install gradle -y
   -gradle -v

3. Run unit tests to make sure the code works:
   -gradle test

4. Build the application and generate the JAR file:
   -gradle clean build

5. Run the application 
   -java -jar build/libs/ivolve-app.jar

Project structure:

build1/
├── build.gradle       # Gradle build file
├── settings.gradle
├── src/               # Java source code
└── build/             # Compiled JAR file
