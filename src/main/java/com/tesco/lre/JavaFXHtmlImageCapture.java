package com.tesco.lre;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
public class JavaFXHtmlImageCapture extends Application {

    private static WebView webView = null;
    private static Stage stage = null;
    private static boolean reloaded;
    ExecutorService executionService = Executors.newVirtualThreadPerTaskExecutor();
    public static ConcurrentLinkedDeque<Future<Boolean>> futureList = new ConcurrentLinkedDeque<>();

    static CountDownLatch countDownLatch = new CountDownLatch(1);

    private ChangeListener<Worker.State> stateListener = (ov, oldState, newState) -> {

        if (newState == Worker.State.SUCCEEDED) {
            if (reloaded) {
                SnapshotParameters params = new SnapshotParameters();
                // Set the background color to transparent
                params.setFill(javafx.scene.paint.Color.TRANSPARENT);
                params.setTransform(javafx.scene.transform.Transform.scale(1, 1));
                // Take a snapshot of the WebView
                WritableImage snapshot = webView.snapshot(params, null);
                BufferedImage capture = SwingFXUtils.fromFXImage(snapshot, null);
                log.info("Captured");
                try {
                    ImageIO.write(capture, "png", new File("C:\\Users\\micro\\IdeaProjects\\lre\\src\\main\\resources\\output"+ UUID.randomUUID().toString()+".png"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log.info("Image written");
                stage.hide();
                countDownLatch.countDown();
            } else {
                reloaded = true;
                webView.getEngine().reload();
            }
        }
    };

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        primaryStage.setScene(new Scene(webView = new WebView()));
        Worker<Void> worker = webView.getEngine().getLoadWorker();
        worker.stateProperty().addListener(stateListener);
        Platform.setImplicitExit(false);
        log.info("started");
    }


    public static  void consume() throws InterruptedException {
        while(true) {
            log.info("Polling");
            countDownLatch = new CountDownLatch(1);
            if (TaskQueue.taskQueue.peekLast() != null) {
                log.info("found");
                String fileName  =  TaskQueue.taskQueue.poll();
                Platform.runLater(() -> {
                    reloaded = false;
                    stage.show(); // JDK-8087569: will not capture without showing stage
                    stage.toBack();
                    webView.getEngine().load(new File(fileName).toURI().toString());
                });
                countDownLatch.await();
            } else{
                Thread.sleep(5000);
            }
        }
    }


    public static  void waitForTasks () throws ExecutionException, InterruptedException {
   /*     while(true) {
            log.info("Waiting for image write");
            if (futureList.peekLast() != null) {

                Future<Boolean> future = futureList.poll();
                Boolean written =   future.get();
                log.info("image written : " + written);
            } else{
                Thread.sleep(5000);
            }*/
        }

}



