package de.dhbw.domainservice;

import de.dhbw.entities.RankingEntity;

import java.util.List;

public interface RankingDomainService {
    boolean saveNewRanking(RankingEntity ranking);
    boolean isTopRanking(RankingEntity ranking);
    List<RankingEntity> getTopRankings();
}
