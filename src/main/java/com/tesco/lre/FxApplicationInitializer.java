package com.tesco.lre;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class FxApplicationInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        new Thread(()-> {
            try {
                JavaFXHtmlImageCapture.consume();
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            }
        }).start();

        new Thread(()-> {
            try {
                JavaFXHtmlImageCapture.waitForTasks();
            } catch (InterruptedException e) {
                log.info(e.getMessage());
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).start();

        Application.launch(JavaFXHtmlImageCapture.class);
    }


}

