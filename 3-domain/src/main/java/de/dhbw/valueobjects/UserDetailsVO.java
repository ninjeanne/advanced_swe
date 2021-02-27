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
@AllArgsConstructor
@Entity
@IdClass(UserDetailsVO.class)
public final class UserDetailsVO implements Serializable {

    @Id
    private final String name;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserDetailsVO) {
            UserDetailsVO userDetails = (UserDetailsVO) obj;
            return this.name.equals(userDetails.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
