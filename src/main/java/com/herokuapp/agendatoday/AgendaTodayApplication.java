package com.herokuapp.agendatoday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class AgendaTodayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgendaTodayApplication.class, args);
    }

}
