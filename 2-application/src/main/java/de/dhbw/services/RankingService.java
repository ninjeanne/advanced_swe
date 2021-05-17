package de.dhbw.services;

import de.dhbw.domainservice.RankingDomainService;
import de.dhbw.entities.RankingEntity;
import de.dhbw.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class RankingService implements RankingDomainService {

    private final RankingRepository rankingRepository;
    private final int NUMBER_OF_RANKINGS = 10;

    @Autowired
    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public boolean saveNewRanking(RankingEntity ranking) {
        List<RankingEntity> rankingEntities = getTopRankings();
        if (isTopRanking(ranking)) {
            if (rankingEntities.size() >= NUMBER_OF_RANKINGS) {
                rankingRepository.delete(rankingEntities.get(rankingEntities.size() - 1));
            }
            rankingRepository.save(ranking);
            return true;
        }
        return false;
    }

    @Override
    public boolean isTopRanking(RankingEntity ranking) {
        List<RankingEntity> rankingEntities = getTopRankings();
        if (rankingEntities.size() < NUMBER_OF_RANKINGS) {
            return true;
        }

        return rankingEntities.get(rankingEntities.size() - 1).getTotal() < ranking.getTotal();
    }

    @Override
    public List<RankingEntity> getTopRankings() {
        List<RankingEntity> entities = rankingRepository.getTopRankings();
        entities.sort(Comparator.comparing(RankingEntity::getTotal).reversed());
        return entities;
    }

}
