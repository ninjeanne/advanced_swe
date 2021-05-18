package de.dhbw.entities.gameobjects;

import de.dhbw.entities.PlayerEntity;
import de.dhbw.entities.board.BoardConfigurationEntity;
import de.dhbw.valueobjects.CoordinatesVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfectionEntity extends GameObjectEntity {
    private BoardConfigurationEntity boardConfigurationEntity;

    private boolean infectByProbability() {
        return Math.random() >= boardConfigurationEntity.getInfectProbability().getProbability();
    }

    @Override
    public void doToPlayer(PlayerEntity playerEntity) {
        if (infectByProbability()) {
           super.doToPlayer(playerEntity);
        }
    }

    @Override
    public int getRankingValue() {
        return 0;
    }

    @Override
    public boolean isInRangeOfGameObject(CoordinatesVO playerCoordinate) {
        double distance =  calculateDistanceBetweenPointsWithHypot(this.getCoordinatesVO(), playerCoordinate);
        return distance <= boardConfigurationEntity.getColleagueRadius().getRadius();
    }

    @Override
    public boolean needsNewCoordinateAfterAction() {
        return false;
    }

    private double calculateDistanceBetweenPointsWithHypot(
            CoordinatesVO coord1,
            CoordinatesVO coord2) {

        double ac = Math.abs(coord2.getY() - coord1.getY());
        double cb = Math.abs(coord2.getX() - coord1.getX());

        return Math.hypot(ac, cb);
    }
}
