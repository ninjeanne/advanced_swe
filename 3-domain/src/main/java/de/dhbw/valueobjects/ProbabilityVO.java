package de.dhbw.valueobjects;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
public final class ProbabilityVO {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private final double probability;

    @SuppressWarnings("unused")
    public ProbabilityVO() {
        this.probability = 0;
    }

    public ProbabilityVO(double probability) {
        if (isValid(probability)) {
            this.probability = probability;
        } else {
            throw new IllegalArgumentException("Probability is invalid for " + probability);
        }
    }

    public ProbabilityVO(long id, double probability) {
        this(probability);
        this.id = id;
    }

    private boolean isValid(double value) {
        return value > 0 && value < 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProbabilityVO) {
            ProbabilityVO probabilityVO = (ProbabilityVO) obj;
            return this.probability == probabilityVO.getProbability();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(probability);
    }
}
