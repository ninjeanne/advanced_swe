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

import java.util.ArrayList;
import java.util.List;
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
        CoordinatesVO vaccination = new CoordinatesVO(20, 5);
        PlanVO plan = new PlanVO(50, 80);
        BoardAggregate board = new BoardAggregate(UUID.randomUUID().toString(), "default", vaccination, plan, 1);
        for (int y = 1; y < 5; y++) {
            for (int i = 0; i <= 12; i++) {
                for (int j = i * 5; j < (i * 5) + 10 && i % 4 == 0; j++) {
                    board.addObstacle(new CoordinatesVO(j, 10 * y));
                }
            }
        }

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int r = 0; r < 2; r++) {
                    for (int s = 0; s < 2; s++) {
                        board.addObstacle(new CoordinatesVO(12 + r + x * 20, 15 + s + y * 10));
                    }
                }
            }
        }

        List<CoordinatesVO> pathOfColleague = new ArrayList<>();

        for (int x = 0; x < 10; x++) {
            pathOfColleague.add(new CoordinatesVO(11 + x, 11));
        }
        for (int y = 0; y < 5; y++) {
            pathOfColleague.add(new CoordinatesVO(21, 11 + y));
        }

        board.addColleague(new ColleagueAggregate("Fred", 0, true, pathOfColleague));
        return (args) -> {
            repository.save(board);
        };
    }
}
