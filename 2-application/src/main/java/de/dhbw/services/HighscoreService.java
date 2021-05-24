package de.dhbw.services;

import de.dhbw.domainservice.HighscoreDomainService;
import de.dhbw.entities.ranking.RankingEntity;
import de.dhbw.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class HighscoreService implements HighscoreDomainService {

    private final RankingRepository rankingRepository;
    public final static int NUMBER_OF_RANKINGS = 10;

    @Autowired
    public HighscoreService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public boolean saveNewRanking(RankingEntity ranking) {
        List<RankingEntity> rankingEntities = getHighscore();
        if (isInHighscore(ranking)) {
            if (rankingEntities.size() >= NUMBER_OF_RANKINGS) {
                rankingRepository.delete(rankingEntities.get(rankingEntities.size() - 1));
            }
            rankingRepository.save(ranking);
            return true;
        }
        return false;
    }

    @Override
    public boolean isInHighscore(RankingEntity ranking) {
        List<RankingEntity> rankingEntities = getHighscore();
        if (rankingEntities.size() < NUMBER_OF_RANKINGS) {
            return true;
        }

        return rankingEntities.get(rankingEntities.size() - 1).getTotal() < ranking.getTotal();
    }

    @Override
    public List<RankingEntity> getHighscore() {
        List<RankingEntity> entities = rankingRepository.getHighscore();
        entities.sort(Comparator.comparing(RankingEntity::getTotal).reversed());
        return entities;
    }

}
