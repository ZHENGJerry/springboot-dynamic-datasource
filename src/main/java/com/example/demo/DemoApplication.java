package com.example.demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
//@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
private static final Log logger = LogFactory.getLog(DemoApplication.class);

    @RequestMapping(value = "/healthz", method = { RequestMethod.GET })
    String home() {
        return "Hello, Spring Boot Demo!";
    }

    public static void main(String[] args) throws Exception {
        logger.debug("Demo Application is starting...");
        new SpringApplicationBuilder(DemoApplication.class)
                .web(WebApplicationType.NONE) // .REACTIVE, .SERVLET
                .bannerMode(Banner.Mode.OFF)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {

            Socket socket = null;
            ServerSocket serverSocket = new ServerSocket(5433);

            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("I/O error: " + e);
                }
                // new thread for a client
                new EchoThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
