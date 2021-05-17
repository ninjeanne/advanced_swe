package de.dhbw.valueobjects;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Entity
@Slf4j
public final class GameObjectCountVO {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private final int count;

    @SuppressWarnings("unused")
    public GameObjectCountVO() {
        this.count = 0;
    }

    public GameObjectCountVO(int count) {
        if (isValid(count)) {
            this.count = count;
        } else {
            throw new IllegalArgumentException("Item is invalid for " + count);
        }
    }

    private boolean isValid(int value) {
        return value >= 0;
    }

    public GameObjectCountVO increasedCount(){
        return new GameObjectCountVO(count+1);
    }

    public GameObjectCountVO decreasedCount(){
        if(isValid(count - 1)){
          return new GameObjectCountVO(count-1);
        }
        log.warn("couldn't reduce GameObjectCount for value {}", count);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GameObjectCountVO) {
            GameObjectCountVO gameObjectCountVO = (GameObjectCountVO) obj;
            return this.count == gameObjectCountVO.getCount();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
