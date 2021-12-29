package ru.yastrebov.onemicr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OneMicrApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneMicrApplication.class, args);
    }
}

