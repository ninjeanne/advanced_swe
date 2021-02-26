package de.dhbw.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Builder
@Getter
@AllArgsConstructor
public final class UserDetailsVO {

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
