package de.dhbw.valueobjects;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
public final class ItemsVO {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private final int numberOfItems;

    @SuppressWarnings("unused")
    public ItemsVO() {
        this.numberOfItems = 0;
    }

    public ItemsVO(int numberOfItems) {
        if (isValid(numberOfItems)) {
            this.numberOfItems = numberOfItems;
        } else {
            throw new IllegalArgumentException("Item is invalid for " + numberOfItems);
        }
    }

    private boolean isValid(int value) {
        return value >= 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ItemsVO) {
            ItemsVO itemsVO = (ItemsVO) obj;
            return this.numberOfItems == itemsVO.getNumberOfItems();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfItems);
    }
}
