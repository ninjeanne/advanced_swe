package de.dhbw.repository;

import de.dhbw.entities.RankingEntity;
import de.dhbw.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RankingRepositoryBridge implements RankingRepository {

    private final SpringDataRankingRepository springDataRankingRepository;

    @Autowired
    public RankingRepositoryBridge(SpringDataRankingRepository springDataRankingRepository) {
        this.springDataRankingRepository = springDataRankingRepository;
    }

    @Override
    public void save(RankingEntity rankingEntity) {
        springDataRankingRepository.save(rankingEntity);
    }

    @Override
    public List<RankingEntity> getTopRankings() {
        return springDataRankingRepository.findAll();
    }

    @Override
    public void delete(RankingEntity rankingEntity) {
        springDataRankingRepository.delete(rankingEntity);
    }
}
