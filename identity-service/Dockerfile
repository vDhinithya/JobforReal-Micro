# Use an official OpenJDK runtime as a parent image (matching your Java 23)
FROM eclipse-temurin:23-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged jar file into the container
COPY target/*.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]