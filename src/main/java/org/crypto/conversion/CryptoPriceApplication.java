package org.crypto.conversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException; // <--- Unused import


@SpringBootApplication
public class CryptoPriceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoPriceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

