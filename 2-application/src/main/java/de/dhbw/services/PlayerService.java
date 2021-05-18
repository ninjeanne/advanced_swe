package de.dhbw.services;

import de.dhbw.domainservice.CountRankingDomainService;
import de.dhbw.domainservice.PlayerDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.entities.gameobjects.GameObjectEntity;
import de.dhbw.entities.gameobjects.InfectionEntity;
import de.dhbw.entities.gameobjects.VaccinationEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlayerService implements PlayerDomainService, CountRankingDomainService {

    private PlayerEntity player;
    private RankingEntity rankingEntity;
    private Timer rankingPointTimer;

    public void initialize(String playerName, List<GameObjectEntity> gameObjectEntities) {
        this.player = new PlayerEntity(playerName, new CoordinatesVO(0, 0), gameObjectEntities);
        this.rankingEntity = new RankingEntity(UUID.randomUUID().toString(), player.getNameAsEntityID(), 0, player.getPlayerStatistics(), new Date());
    }

    @Override
    public void setNewPositionForPlayer(CoordinatesVO coordinatesVO) {
        this.player.setPosition(coordinatesVO);
    }

    @Override
    public PlayerEntity getCurrentPlayer() {
        return this.player;
    }

    public boolean isAlive() {
        return player.getPlayerStatistics().getStatistic(VaccinationEntity.class).getCount() >= player.getPlayerStatistics().getStatistic(InfectionEntity.class).getCount();
    }

    public boolean isInitialized() {
        return player != null && rankingEntity != null;
    }

    @Override
    public void reset() {
        stopCountingRankingPoints();
        this.player = null;
    }

    @Override
    public void startCountingRankingPoints() {
        if (rankingPointTimer == null) {
            TimerTask rankingPointTask = new TimerTask() {
                public void run() {
                    rankingEntity = new RankingEntity(UUID.randomUUID().toString(), player.getNameAsEntityID(), rankingEntity.getEarned_points() + 1,
                            player.getPlayerStatistics(), rankingEntity.getDate());
                }
            };
            rankingPointTimer = new Timer("Increase Ranking Points");
            rankingPointTimer.scheduleAtFixedRate(rankingPointTask, 0, 500);
        }
    }

    @Override
    public RankingEntity getRankingEntityForPlayer() {
        return rankingEntity;
    }

    @Override
    public void stopCountingRankingPoints() {
        rankingPointTimer.cancel();
        rankingPointTimer = null;
    }

}
