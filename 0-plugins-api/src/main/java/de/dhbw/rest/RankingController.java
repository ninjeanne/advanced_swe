package de.dhbw.rest;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.mapper.RankingMapper;
import de.dhbw.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;
    private final RankingMapper rankingMapper;

    @Autowired
    public RankingController(RankingService rankingService, RankingMapper rankingMapper) {
        this.rankingService = rankingService;
        this.rankingMapper = rankingMapper;
    }

    @GetMapping
    public List<RankingDTO> getRankingsForBoardName(@RequestParam String boardName) {
        return rankingService.getTopRankingsForBoard(boardName).stream().map(rankingMapper).collect(Collectors.toList());
    }
}
