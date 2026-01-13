Lab 2: Java Application with Maven

1.Clone the project
  -git clone https://github.com/Ibrahim-Adel15/build2.git
  -cd build2

2-Install Maven (if needed)
  -sudo apt install maven -y
  -mvn -v

3.Run Unit Tests
  -mvn test

4.Build the App
  -mvn clean package

5.Run the App
  -java -jar target/hello-ivolve-1.0-SNAPSHOT.jar


Project Structure

build2/
├── pom.xml       # Maven build file
├── src/          # Java source code
└── target/       # Generated JAR

