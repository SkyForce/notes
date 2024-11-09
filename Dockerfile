# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file
COPY target/notes-0.0.1-SNAPSHOT.jar notesapp.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "notesapp.jar"]
