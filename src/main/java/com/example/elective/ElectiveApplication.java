package com.example.elective;

import com.blinkfox.fenix.EnableFenix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFenix
@SpringBootApplication
public class ElectiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectiveApplication.class, args);
    }

}
