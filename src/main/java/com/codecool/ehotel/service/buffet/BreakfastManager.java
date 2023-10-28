package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.util.*;

/* data manager class // model
 *
 * keeps track of the meals, their types, quantities
 * provides methods to interact with this data
 * adding a meal portion, getting the freshest meal
 */

public class BreakfastManager {

  private Map<MealType, List<MealPortion>> meals;

  public BreakfastManager() {
    this.meals = new HashMap<>();

    for (MealType type : MealType.values()) {
      this.meals.put(type, new ArrayList<>());
    }
  }

  // add meal portion to buffet
  public void addMealPortion(MealPortion portion) {
    meals.get(portion.getType()).add(portion);
  }

  // get list of all prepared meals
  public List<MealPortion> getAllMeals() {
    List<MealPortion> allMeals = new ArrayList<>();
    for (List<MealPortion> queue : meals.values()) {
      allMeals.addAll(queue);
    }
    return allMeals;
  }

  public MealPortion getFreshestPortion(MealType type) {
    List<MealPortion> mealPortions = meals.get(type);
    if (mealPortions != null && !mealPortions.isEmpty()) {
      return mealPortions.get(mealPortions.size() - 1); // get last element of list
    }
    return null;  // TODO: add msg
  }

  public MealPortion getLeastFreshPortion(MealType type) {
    List<MealPortion> mealPortions = meals.get(type);
    if (mealPortions != null && !mealPortions.isEmpty()) {
      return mealPortions.get(0); // get first element of list
    }
    return null;  // TODO: add msg
  }

  public boolean consumeFreshest(MealType type) {
    List<MealPortion> mealPortions = meals.get(type);
    if (mealPortions != null && !mealPortions.isEmpty()) {
      MealPortion freshestPortion = getFreshestPortion(type);
      return mealPortions.remove(freshestPortion);
    }
    return false;
  }

  public List<MealPortion> getAllMealsOfType(MealType type) {
    return meals.getOrDefault(type, new ArrayList<>());
  }

}
