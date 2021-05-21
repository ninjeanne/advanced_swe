package de.dhbw.domainservice;

import de.dhbw.entities.ranking.RankingEntity;

import java.util.List;

public interface HighscoreDomainService {
    boolean saveNewRanking(RankingEntity ranking);
    boolean isInHighscore(RankingEntity ranking);
    List<RankingEntity> getHighscore();
}
