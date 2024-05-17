# Use a base image with Ubuntu 20.04
# Use Alpine Linux as base image
# Start with Alpine Linux as base image
FROM openjdk:22-slim

# Install necessary packages
RUN apt-get update && apt-get install \
    xvfb \
    libgtk-3-0 \
    unzip \
    -y  && rm -rf /var/cache/apk/*

WORKDIR /app
COPY openjfx-sdk-22.zip openjfx-sdk-22.zip
RUN unzip openjfx-sdk-22.zip -d /usr/lib && rm openjfx-sdk-22.zip
COPY target/classes/ticket.html /usr/lib/ticket.html
COPY target/lre-0.0.1-SNAPSHOT.jar /app/YourJavaFXApplication.jar
ENV DISPLAY=:99
COPY entrypoint.sh /app/
RUN chmod +x /app/entrypoint.sh
EXPOSE 8080
ENTRYPOINT ["/app/entrypoint.sh"]

