package de.dhbw.mapper;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.valueobjects.RankingVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RankingMapper implements Function<RankingVO, RankingDTO> {

    private RankingDTO map(RankingVO rankingVO) {
        //todo implement me
        return null;
    }

    @Override
    public RankingDTO apply(RankingVO rankingVO) {
        return map(rankingVO);
    }
}
