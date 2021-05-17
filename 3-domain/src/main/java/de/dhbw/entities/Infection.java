package de.dhbw.entities;

import de.dhbw.valueobjects.ProbabilityVO;
import lombok.Getter;

@Getter
public class Infection extends GameObject {
    private ProbabilityVO infectProbability; //todo woher kommt diese? wie init?

    public boolean infectByProbability() {
        return Math.random() >= getInfectProbability().getProbability();
    }

    @Override
    public void doToPlayer(PlayerEntity playerEntity) {
        if (infectByProbability()) {
           super.doToPlayer(playerEntity);
        }
    }

    @Override
    public int getValue() {
        return 0;
    }
}
