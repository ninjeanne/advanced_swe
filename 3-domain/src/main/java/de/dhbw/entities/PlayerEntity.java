package de.dhbw.entities;

import de.dhbw.valueobjects.CoordinatesVO;
import de.dhbw.valueobjects.UserDetailsVO;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class PlayerEntity {

    @NonNull
    private UserDetailsVO userDetails; //userDetails as entity id
    private CoordinatesVO position;
    private int lifePoints = 0;

    /**
     * the new position is only allowed to differ in one coordinate and by one step!
     *
     * @param newPosition the new coordinates
     * @return true if the new position has been valid, otherwise false
     */
    public boolean setPosition(CoordinatesVO newPosition) {
        if (position.equals(newPosition)) {
            return true;
        }
        if (position.getY() == newPosition.getY()) {
            return position.getX() == (newPosition.getX() + 1) || position.getX() == (newPosition.getX() - 1);
        } else if (position.getX() == newPosition.getX()) {
            return position.getY() == (newPosition.getY() + 1) || position.getY() == (newPosition.getY() - 1);
        }
        return false;
    }

    public void decreaseLifePoints() {
        if (lifePoints > 0) {
            lifePoints--;
            log.debug("Life points have been decreased for {}. Left: {}", userDetails.getName(), lifePoints);
        } else {
            throw new IllegalArgumentException("Player " + userDetails.getName() + " is already dead.");
        }
    }

    public void increaseLifePoints() {
        lifePoints++;
    }

    public boolean isAlive() {
        return lifePoints > 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) obj;
            return this.userDetails.equals(player.userDetails);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDetails);
    }
}
