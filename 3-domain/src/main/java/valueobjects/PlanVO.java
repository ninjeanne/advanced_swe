package valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
public final class PlanVO {

    private final int length;
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
