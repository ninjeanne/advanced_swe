package de.dhbw.controller;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.mapper.RankingMapper;
import de.dhbw.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private RankingService rankingService;
    private RankingMapper rankingMapper;

    @Autowired
    public RankingController(RankingService rankingService, RankingMapper rankingMapper) {
        this.rankingService = rankingService;
        this.rankingMapper = rankingMapper;
    }

    @GetMapping
    public List<RankingDTO> getRankingsForBoardName() {
        //todo implement me
        return new ArrayList<>();
    }
}
