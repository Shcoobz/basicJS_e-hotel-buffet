package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.Buffet;
import com.codecool.ehotel.model.MealType;

import java.util.*;

public class BuffetManager {

  private Map<MealType, List<MealPortion>> meals;

  public BuffetManager() {
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

  public Buffet getBuffetSnapshot() {
    return new Buffet(new HashMap<>(meals));  // create a copy to ensure immutability
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
}
