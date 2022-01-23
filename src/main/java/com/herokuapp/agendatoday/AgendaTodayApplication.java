package com.herokuapp.agendatoday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.herokuapp.agendatoday.dao")
@EntityScan("com.herokuapp.agendatoday.Entities")
public class AgendaTodayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgendaTodayApplication.class, args);
    }

}
