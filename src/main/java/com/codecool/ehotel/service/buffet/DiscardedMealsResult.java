package com.codecool.ehotel.service.buffet;

import java.util.List;

public class DiscardedMealsResult {
  private List<BuffetMealPortion> discardedMeals;
  private int totalCost;

  public DiscardedMealsResult(List<BuffetMealPortion> discardedMeals, int totalCost) {
    this.discardedMeals = discardedMeals;
    this.totalCost = totalCost;
  }

  public List<BuffetMealPortion> getDiscardedMeals() {
    return discardedMeals;
  }

  public int getTotalCost() {
    return totalCost;
  }
}
