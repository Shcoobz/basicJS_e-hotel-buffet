package com.codecool.ehotel.model;

/**
 * Enumeration representing the durability of a meal, classified into different categories based on duration.
 * This enum defines the durability of meals in terms of their freshness or suitability for consumption
 * over varying lengths of time, specified in minutes.
 */
public enum MealDurability {
    SHORT(90),
    MEDIUM(240),
    LONG(480);

    private final int durationInMinutes;

    /**
     * Constructor for the MealDurability enumeration.
     * Initializes a meal durability category with a specified duration in minutes.
     *
     * @param durationInMinutes The duration in minutes that classifies the meal's durability.
     */
    MealDurability(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    /**
     * Retrieves the duration in minutes associated with a meal durability category.
     *
     * @return The duration in minutes of the meal's durability.
     */
    public int getDurationInMinutes() {
        return durationInMinutes;
    }
}
