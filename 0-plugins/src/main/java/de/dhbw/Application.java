package de.dhbw;

import de.dhbw.repositories.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("de.dhbw.*")
@ComponentScan("de.dhbw.*")
@EnableJpaRepositories("de.dhbw.*")
@Slf4j
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(BoardRepository repository) {
        return (args) -> {
           // repository.save(new BoardAggregate()); //todo
        };
    }
}
