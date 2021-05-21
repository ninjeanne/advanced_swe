package de.dhbw.rest;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.mapper.RankingDTOMapper;
import de.dhbw.services.HighscoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/highscore")
public class HighscoreController {

    private final HighscoreService rankingService;
    private final RankingDTOMapper rankingDTOMapper;

    @Autowired
    public HighscoreController(HighscoreService rankingService, RankingDTOMapper rankingDTOMapper) {
        this.rankingService = rankingService;
        this.rankingDTOMapper = rankingDTOMapper;
    }

    @GetMapping
    public List<RankingDTO> getHighscore() {
        return rankingService.getHighscore().stream().map(rankingDTOMapper).collect(Collectors.toList());
    }
}
