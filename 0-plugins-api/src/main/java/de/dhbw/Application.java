package de.dhbw;

import de.dhbw.aggregates.BoardAggregate;
import de.dhbw.aggregates.ColleagueAggregate;
import de.dhbw.entities.RankingEntity;
import de.dhbw.repositories.BoardRepository;
import de.dhbw.services.RankingService;
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
import java.util.Date;
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
    public CommandLineRunner demo(BoardRepository boardRepository, RankingService rankingService) {
        PlanVO plan = new PlanVO(50, 80);
        BoardAggregate board = new BoardAggregate(UUID.randomUUID().toString(), "default", plan);
        board.setProbability(0.5);
        board.setColleagueRadius(3);
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
        RankingEntity rankingEntity = new RankingEntity(UUID.randomUUID().toString(), "Ninjeanne", 123456, 0, new Date());
        return (args) -> {
            boardRepository.save(board);
            rankingService.saveNewRankingForBoard(rankingEntity, board.getName());
        };
    }
}
