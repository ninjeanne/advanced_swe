package de.dhbw.helper;

import de.dhbw.entities.board.ColleagueEntity;
import de.dhbw.valueobjects.CoordinatesVO;

/**
 * Design Pattern: Iterator!
 */
public class ForwardAndBackMovement implements ColleagueMovement {

    private final ColleagueEntity aggregate;
    private int position = 0;
    private boolean moveForward = true;

    public ForwardAndBackMovement(ColleagueEntity aggregate){
        this.aggregate = aggregate;
    }

    public CoordinatesVO getCurrentPosition() {
        return aggregate.getPath().get(position);
    }

    private void setPosition(int position) {
        if (position >= 0 && position < aggregate.getPath().size()) {
            this.position = position;
            return;
        }

        throw new IllegalArgumentException(
                "The position has to be positive and part of the path. " + "Path Size is: " + aggregate.getPath().size() + ", new position: " + position
                        + ", old position: " + this.position);
    }

    public CoordinatesVO nextPosition() {
        if (moveForward) {
            if (position + 1 == aggregate.getPath().size()) {
                moveForward = false;
                return nextPosition();
            } else {
                setPosition(++position);
                return aggregate.getPath().get(position);
            }
        } else {
            if (position == 0) {
                moveForward = true;
                return nextPosition();
            } else {
                setPosition(--position);
                return aggregate.getPath().get(position);
            }
        }
    }

    @Override
    public ColleagueEntity getColleagueEntity() {
        return aggregate;
    }

}
