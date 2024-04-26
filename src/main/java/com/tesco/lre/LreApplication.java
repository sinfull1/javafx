package com.tesco.lre;

import jakarta.annotation.PreDestroy;
import javafx.application.Application;
import javafx.application.Platform;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import javax.swing.*;

@SpringBootApplication
public class LreApplication {

    public static void main(String[] args) {
           SpringApplication.run(LreApplication.class, args);
    }

    @PreDestroy
    public void destroy() {
        Platform.exit();
    }

}
