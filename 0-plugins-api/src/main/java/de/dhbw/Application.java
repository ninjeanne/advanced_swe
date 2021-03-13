package de.dhbw;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.PlanVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.UUID;

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
        BoardAggregate board = new BoardAggregate(UUID.randomUUID().toString(), "default", new CoordinatesVO(10, 5), new PlanVO(500, 1000), 1);
        board.addObstacle(new CoordinatesVO(3, 2));
        board.addColleague(new ColleagueAggregate("Fred", 0, true,
                Arrays.asList(new CoordinatesVO(12, 12), new CoordinatesVO(12, 13), new CoordinatesVO(12, 14), new CoordinatesVO(13, 14))));
        return (args) -> {
            repository.save(board);
        };
    }
}
