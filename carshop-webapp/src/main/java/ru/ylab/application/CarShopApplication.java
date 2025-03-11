package ru.ylab.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ylab.logging.EnableLoggingAspect;


@SpringBootApplication(scanBasePackages = "ru.ylab")
@EnableLoggingAspect
public class CarShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarShopApplication.class, args);
    }

}
