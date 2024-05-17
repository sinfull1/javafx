package com.tesco.lre;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@RestController
public class ImageController {


    Random random = new Random();

    @GetMapping("/generate")
    public String generateImage() throws IOException, InterruptedException {
        TaskQueue.taskQueue.add("ticket.html");
        return "Completed";
    }

}
