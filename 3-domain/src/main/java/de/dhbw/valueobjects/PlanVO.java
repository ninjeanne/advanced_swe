package de.dhbw.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Entity
@AllArgsConstructor
@IdClass(PlanVO.class)
public final class PlanVO implements Serializable {

    @Id
    private final int length;
    @Id
    private final int width;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlanVO) {
            PlanVO plan = (PlanVO) obj;
            return this.length == plan.length && this.width == plan.width;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, width);
    }
}
