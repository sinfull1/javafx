# Use the official Azul Zulu OpenJDK 22 Alpine Linux image
FROM kmarasovic/alpine-jdkfx17-ant-mvn

WORKDIR /app

# Copy and unzip the OpenJFX SDK, then remove the zip file in one layer
RUN apk add --no-cache \
      libx11 xvfb gtk+3.0 ttf-freefont gcompat\
    && rm -rf /var/cache/apk/*

# Copy the HTML and JAR files to the appropriate locations
COPY target/classes/ticket.html /usr/lib/ticket.html
COPY target/lre-0.0.1-SNAPSHOT.jar /app/YourJavaFXApplication.jar

# Set the display environment variable for Xvfb
ENV DISPLAY=:99



# Set the Java options to include the necessary library path
# (Assuming this is needed for your JavaFX application, include actual options if any)

# Copy the entrypoint script and make it executable
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Expose the necessary port
EXPOSE 8080

# Define the entrypoint
ENTRYPOINT ["/app/entrypoint.sh"]
