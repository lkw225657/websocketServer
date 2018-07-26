package net.transino.lms;

import net.transino.lms.modules.comm.client.SocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
         new SpringApplication (Application.class).run(args);
    }

//    @PostConstruct
//    public void init(){
//        SocketClient client = (SocketClient) ctx.getBean("client");
//
//    }
}