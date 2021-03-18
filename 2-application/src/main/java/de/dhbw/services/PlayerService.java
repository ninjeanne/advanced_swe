package de.dhbw.services;

import de.dhbw.domainservice.PlayerDomainService;
import de.dhbw.entities.PlayerEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.ItemsVO;
import org.springframework.stereotype.Component;

@Component
public class PlayerService implements PlayerDomainService {

    private PlayerEntity player;

    @Override
    public void initialize(String playerName) {
        this.player = new PlayerEntity(playerName, new CoordinatesVO(0, 0), new ItemsVO(3), new ItemsVO(0));
        this.player.setPosition(new CoordinatesVO(0, 0));
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
    public boolean isAlive(){
        return player.isAlive();
    }

    @Override
    public boolean isInitialized() {
        return player != null;
    }

    @Override
    public void resetPlayer() {
        this.player = null;
    }

}
