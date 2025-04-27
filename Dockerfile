# Use an official OpenJDK 17 runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR from the target directory into the container
# (Make sure to run 'mvn clean package' before building the Docker image)
COPY target/queue-booking-system-0.0.1.jar app.jar

# Expose the port that your microservice uses
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]