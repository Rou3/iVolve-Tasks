# استخدم Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# اعمل work directory جوا الكونتينر
WORKDIR /app

# انسخ الـ JAR المبني من target
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# اعرض البورت اللي التطبيق هيشتغل عليه
EXPOSE 8080

# شغل التطبيق
ENTRYPOINT ["java", "-jar", "app.jar"]

