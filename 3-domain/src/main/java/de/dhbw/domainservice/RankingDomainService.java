package de.dhbw.domainservice;

import de.dhbw.entities.RankingEntity;

import java.util.List;

public interface RankingDomainService {
    boolean saveNewRankingForBoard(RankingEntity ranking, String boardName);
    boolean isTopRankingInBoard(RankingEntity ranking, String boardName);
    List<RankingEntity> getTopRankingsForBoard(String boardName);
}
