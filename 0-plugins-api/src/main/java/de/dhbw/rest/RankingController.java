package de.dhbw.rest;

import de.dhbw.entities.RankingEntity;
import de.dhbw.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    @Autowired
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public List<RankingEntity> getRankingsForBoardName(@RequestParam String boardName) {
        return rankingService.getTopRankingsForBoard(boardName);
    }
}
