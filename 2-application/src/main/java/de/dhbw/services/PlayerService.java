package de.dhbw.services;

import de.dhbw.domainservice.PlayerDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.RankingEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.ItemsVO;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Component
public class PlayerService implements PlayerDomainService {

    private PlayerEntity player;
    private RankingEntity rankingEntity;
    private Timer rankingPointTimer;

    @Override
    public void initialize(String playerName) {
        this.player = new PlayerEntity(playerName, new CoordinatesVO(0, 0), new ItemsVO(3), new ItemsVO(0));
        this.player.setPosition(new CoordinatesVO(0, 0));
        this.rankingEntity = new RankingEntity(UUID.randomUUID().toString(), player.getName(), 0, player.getWorkItems(), new Date());
    }

    @Override
    public void setNewPosition(CoordinatesVO coordinatesVO) {
        this.player.setPosition(coordinatesVO);
    }

    @Override
    public void work() {
        player.increaseWorkItems();
    }

    @Override
    public boolean vaccinate() {
        if (player.isAlive()) {
            player.increaseLifePoints();
            return true;
        }
        return false;
    }

    @Override
    public void infect(boolean infected) {
        if (player.isAlive() && infected) {
            player.decreaseLifePoints();
        }
    }

    @Override
    public PlayerEntity getCurrentPlayer() {
        return this.player;
    }

    @Override
    public boolean isAlive() {
        return player.isAlive();
    }

    @Override
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
                    rankingEntity = new RankingEntity(UUID.randomUUID().toString(), player.getName(), rankingEntity.getEarned_points() + 1,
                            player.getWorkItems(), rankingEntity.getDate());
                }
            };
            rankingPointTimer = new Timer("Increase Ranking Points");
            rankingPointTimer.scheduleAtFixedRate(rankingPointTask, 0, 500);
        }
    }

    @Override
    public RankingEntity getRankingEntity() {
        return rankingEntity;
    }

    @Override
    public void stopCountingRankingPoints() {
        rankingPointTimer.cancel();
        rankingPointTimer = null;
    }

}
