package com.harihandle.java.spring.springbootvaultdemo;

import com.harihandle.java.spring.springbootvaultdemo.model.MyCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootVaultDemoApplication implements CommandLineRunner {

    @Autowired
    private MyCredentials myCredentials;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootVaultDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(myCredentials.toString());
    }

}
