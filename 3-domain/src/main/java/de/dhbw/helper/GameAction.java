package de.dhbw.helper;

import de.dhbw.entities.GameObject;

public abstract class GameAction<T extends GameObject> {
    private final Class<T> type;

    public GameAction(Class<T> type) {
        this.type = type;
    }

    public Class<T> getType() {
        return this.type;
    }

    public abstract void doActionOn(T object);
}
