package de.dhbw.entities;

import org.springframework.stereotype.Component;

@Component
public class WorkItem extends GameObject {
    @Override
    public int getRankingValue() {
        return 50;
    }
}
