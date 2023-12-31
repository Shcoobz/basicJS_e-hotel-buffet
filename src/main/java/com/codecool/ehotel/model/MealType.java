package com.codecool.ehotel.model;

import static com.codecool.ehotel.model.MealDurability.*;

/**
 * Enumeration representing different types of meals, each associated with a specific cost and durability.
 * This enum defines various meals offered, categorizing them by their cost and the duration for which they remain fresh or suitable for consumption.
 */
public enum MealType {
    SCRAMBLED_EGGS (70, SHORT),
    SUNNY_SIDE_UP (70, SHORT),
    FRIED_SAUSAGE(100, SHORT),
    FRIED_BACON(70, SHORT),
    PANCAKE(40, SHORT),
    CROISSANT(40, SHORT),
    MASHED_POTATO(20, MEDIUM),
    MUFFIN(20, MEDIUM),
    BUN(10, MEDIUM),
    CEREAL(30, LONG),
    MILK(10, LONG);

    private int cost;
    private MealDurability mealDurability;

    /**
     * Constructor for the MealType enumeration.
     * Initializes a meal type with a specified cost and meal durability category.
     *
     * @param cost The cost of the meal.
     * @param mealDurability The durability of the meal, as defined in the MealDurability enum.
     */
    MealType(int cost, MealDurability mealDurability) {
        this.cost = cost;
        this.mealDurability = mealDurability;
    }

    /**
     * Retrieves the cost associated with a meal type.
     *
     * @return The cost of the meal.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Retrieves the durability category of the meal type.
     * The durability indicates the time duration for which the meal remains fresh or suitable for consumption.
     *
     * @return The MealDurability of the meal.
     */
    public MealDurability getDurability() {
        return mealDurability;
    }
}
