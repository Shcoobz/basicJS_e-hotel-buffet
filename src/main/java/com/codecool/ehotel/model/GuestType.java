package com.codecool.ehotel.model;

import java.util.List;
import static com.codecool.ehotel.model.MealType.*;

/**
 * Enumeration representing different types of guests at a hotel, each with their specific meal preferences.
 * This enum categorizes guests into types like BUSINESS, TOURIST, and KID, associating each type with a list of preferred meals.
 */
public enum GuestType {

    BUSINESS(List.of(SCRAMBLED_EGGS, FRIED_BACON, CROISSANT)),
    TOURIST(List.of(SUNNY_SIDE_UP, FRIED_SAUSAGE, MASHED_POTATO, BUN, MUFFIN)),
    KID(List.of(PANCAKE, MUFFIN, CEREAL, MILK));

    private List<MealType> mealPreferences;

    /**
     * Constructor for the GuestType enumeration.
     * Initializes a guest type with a specified list of meal preferences.
     *
     * @param mealPreferences The list of MealType preferences associated with the guest type.
     */
    GuestType(List<MealType> mealPreferences) {
        this.mealPreferences = mealPreferences;
    }

    /**
     * Retrieves the meal preferences associated with a guest type.
     * The preferences are a list of MealType enumerations.
     *
     * @return A list of MealType preferences associated with the guest type.
     */
    public List<MealType> getMealPreferences() {
        return mealPreferences;
    }
}
