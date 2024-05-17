#!/bin/sh

# Start Xvfb
rm /tmp/.X99-lock #needed when docker container is restarted
Xvfb :99 -screen 0 640x480x8  +extension GLX +render -noreset &

# Run the JavaFX application
java -Dprism.order=sw -Djavafx.verbose=true --module-path /usr/lib/javafx-sdk-22/lib/ \
    --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.web \
    --add-reads javafx.graphics=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.javafx.scene=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.javafx.util=ALL-UNNAMED \
    --add-exports javafx.base/com.sun.javafx.logging=ALL-UNNAMED \
    --add-exports javafx.web/com.sun.webkit.dom=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.prism=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.glass.ui=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.javafx.geom.transform=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.javafx.tk=ALL-UNNAMED \
    --add-exports javafx.graphics/com.sun.glass.utils=ALL-UNNAMED \
    -jar /app/YourJavaFXApplication.jar

