package de.dhbw.dao;

import de.dhbw.valueobjects.CoordinatesVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CoordinatesDAO implements Serializable {

    @Id
    private int x;
    @Id
    private int y;

    public CoordinatesVO toVO() {
        return new CoordinatesVO(x, y);
    }

    public static CoordinatesDAO toDAO(CoordinatesVO coordinatesVO) {
        return CoordinatesDAO.builder().x(coordinatesVO.getX()).y(coordinatesVO.getY()).build();
    }

}
