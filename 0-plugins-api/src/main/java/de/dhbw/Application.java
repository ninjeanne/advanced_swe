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
        BoardAggregate board = BoardAggregate.builder().name("default").uuid(UUID.randomUUID().toString())
                .vaccination(CoordinatesVO.builder().id(0).x(10).y(5).build()).plan(PlanVO.builder().length(50).width(50).build()).velocity(1).build();
        board.addObstacle(CoordinatesVO.builder().x(3).y(2).build());
        board.addColleague(ColleagueAggregate.builder().name("Fred").moveForward(true).path(Arrays
                .asList(CoordinatesVO.builder().x(12).y(12).build(), CoordinatesVO.builder().x(12).y(13).build(), CoordinatesVO.builder().x(12).y(14).build(),
                        CoordinatesVO.builder().x(13).y(14).build())).build());
        return (args) -> {
            repository.save(board);
        };
    }
}
