# Set the base image
FROM adoptopenjdk/openjdk8:alpine-jre

# Set the working directory
WORKDIR /app

# Copy the application JAR file to the container
COPY target/pidev-0.0.1-SNAPSHOT.jar /app

# Expose the port on which the application will run
EXPOSE 8090

# Set the command to run when the container starts
CMD ["java", "-jar", "pidev-0.0.1-SNAPSHOT.jar"]