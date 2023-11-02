package com.codecool.ehotel.model;

public enum MealDurability {
    SHORT(90),
    MEDIUM(240),
    LONG(480);

    private final int durationInMinutes;

    MealDurability(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }
}
