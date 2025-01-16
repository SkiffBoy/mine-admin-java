package com.skiffboy.mine;

import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFileStorage
public class MineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MineApplication.class, args);
    }

}
