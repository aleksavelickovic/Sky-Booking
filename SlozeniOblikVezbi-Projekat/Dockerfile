# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file from the host machine to the container
# Assuming your JAR file is located in 'target' directory and is named 'PrviMavenVebProjekat.jar'
COPY target/PrviMavenVebProjekat.jar /app/PrviMavenVebProjekat.jar

# Expose the port that the app will run on (default for Spring Boot is 8080)
EXPOSE 8080

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "/app/PrviMavenVebProjekat.jar"]