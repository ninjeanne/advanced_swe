package de.dhbw.dao;

import de.dhbw.valueobjects.PlanVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PlanDAO implements Serializable {

    @Id
    private int length;
    @Id
    private int width;

    public PlanVO toVO() {
        return new PlanVO(length, width);
    }

    public static PlanDAO toDAO(PlanVO planVO) {
        return PlanDAO.builder().length(planVO.getLength()).width(planVO.getWidth()).build();
    }
}
