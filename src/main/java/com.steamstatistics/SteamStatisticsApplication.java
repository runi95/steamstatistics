package com.steamstatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SteamStatisticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteamStatisticsApplication.class, args);
    }
}