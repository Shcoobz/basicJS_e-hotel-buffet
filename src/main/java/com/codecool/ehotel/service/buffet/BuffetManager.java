package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.util.*;

/* data manager class // model
 *
 * keeps track of the meals, their types, quantities
 * provides methods to interact with this data
 * adding a meal portion, getting the freshest meal
 */

public class BuffetManager {

  private Map<MealType, List<BuffetMealPortion>> meals;

  public BuffetManager() {
    this.meals = new HashMap<>();

    for (MealType type : MealType.values()) {
      this.meals.put(type, new ArrayList<>());
    }
  }

  // add meal portion to buffet
  public void addMealPortion(BuffetMealPortion portion) {
    meals.get(portion.getType()).add(portion);
  }

  // get list of all prepared meals
  public List<BuffetMealPortion> getAllMeals() {
    List<BuffetMealPortion> allMeals = new ArrayList<>();
    for (List<BuffetMealPortion> queue : meals.values()) {
      allMeals.addAll(queue);
    }
    return allMeals;
  }

  public BuffetMealPortion getFreshestPortion(MealType type) {
    List<BuffetMealPortion> buffetMealPortions = meals.get(type);
    if (buffetMealPortions != null && !buffetMealPortions.isEmpty()) {
      return buffetMealPortions.get(buffetMealPortions.size() - 1); // get last element of list
    }
    return null;  // TODO: do something
  }

  public BuffetMealPortion getLeastFreshPortion(MealType type) {
    List<BuffetMealPortion> buffetMealPortions = meals.get(type);
    if (buffetMealPortions != null && !buffetMealPortions.isEmpty()) {
      return buffetMealPortions.get(0); // get first element of list
    }
    return null;  // TODO: do something
  }

  public boolean consumeFreshest(MealType type) {
    List<BuffetMealPortion> buffetMealPortions = meals.get(type);
    if (buffetMealPortions != null && !buffetMealPortions.isEmpty()) {
      BuffetMealPortion freshestPortion = getFreshestPortion(type);
      return buffetMealPortions.remove(freshestPortion);
    }
    return false;
  }

  public List<BuffetMealPortion> getAllMealsOfType(MealType type) {
    return meals.getOrDefault(type, new ArrayList<>());
  }

  public int getCountOfMealType(MealType type) {
    return (int) getAllMeals().stream().filter(meal -> meal.getType() == type).count();
  }




}
