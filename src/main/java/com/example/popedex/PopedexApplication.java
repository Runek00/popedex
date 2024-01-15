package com.example.popedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class PopedexApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopedexApplication.class, args);
    }

    @Bean
//    @Profile("dev")
    public DataSource dataSourceDev() {
        var dsb = DataSourceBuilder.create();
        dsb.driverClassName("org.postgresql.Driver");
        dsb.url("jdbc:postgresql://localhost:5432/popedex");
        dsb.username("postgres");
        dsb.password("popedex");
        return dsb.build();
    }
}

