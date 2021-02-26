package de.dhbw.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
@Entity
public final class UserDetailsVO {

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
