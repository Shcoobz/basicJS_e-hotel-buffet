package com.codecool.ehotel.service.buffet;

import java.util.List;

/* data structure
 *
 * encapsulates result of a discard operation in the buffet service
 * holds a collection of meals that have been discarded plus total costs
 *
 * 'discardedMeals' list contains each meal portion that was discarded
 * 'totalCost' represents costs associated with these meals
 */


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
