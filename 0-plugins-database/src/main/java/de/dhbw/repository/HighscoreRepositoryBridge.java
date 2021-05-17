package de.dhbw.repository;

import de.dhbw.entities.RankingEntity;
import de.dhbw.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HighscoreRepositoryBridge implements RankingRepository {

    private final SpringDataHighscoreRepository springDataHighscoreRepository;

    @Autowired
    public HighscoreRepositoryBridge(SpringDataHighscoreRepository springDataHighscoreRepository) {
        this.springDataHighscoreRepository = springDataHighscoreRepository;
    }

    @Override
    public void save(RankingEntity rankingEntity) {
        springDataHighscoreRepository.save(rankingEntity);
    }

    @Override
    public List<RankingEntity> getHighscore() {
        return springDataHighscoreRepository.findAll();
    }

    @Override
    public void delete(RankingEntity rankingEntity) {
        springDataHighscoreRepository.delete(rankingEntity);
    }
}
