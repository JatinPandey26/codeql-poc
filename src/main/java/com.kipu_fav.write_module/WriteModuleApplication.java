package com.kipu_fav.write_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class WriteModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WriteModuleApplication.class, args);
    }

}
