# Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the JAR file from target directory
COPY target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Add a non-root user for security
RUN addgroup --system spring && adduser --system spring --ingroup spring
USER spring:spring

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Optional: Add JVM options for production
# ENTRYPOINT ["java", "-Xms512m", "-Xmx1024m", "-jar", "app.jar"]