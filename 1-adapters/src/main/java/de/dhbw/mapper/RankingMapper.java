package de.dhbw.mapper;

import de.dhbw.dtos.RankingDTO;
import de.dhbw.valueobjects.RankingVO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RankingMapper implements Function<RankingDTO, RankingVO> {

    private RankingVO map(RankingDTO rankingDTO) {
        //todo implement me
        return null;
    }

    @Override
    public RankingVO apply(RankingDTO rankingDTO) {
        return map(rankingDTO);
    }
}
