package com.codecool.ehotel.service.buffet;

import java.util.List;

/**
 * Encapsulates the result of a discard operation in the buffet service.
 * This class holds a collection of meals that have been discarded and the total cost associated with these meals.
 *
 * It provides access to both the list of discarded meal portions and the total cost incurred due to the discarding.
 */
public class DiscardedMealsResult {
  private List<BuffetMealPortion> discardedMeals;
  private int totalCost;

  /**
   * Constructs a DiscardedMealsResult instance with specified discarded meals and their total cost.
   *
   * @param discardedMeals A list of BuffetMealPortion representing each meal portion that was discarded.
   * @param totalCost The total cost associated with the discarded meals.
   */
  public DiscardedMealsResult(List<BuffetMealPortion> discardedMeals, int totalCost) {
    this.discardedMeals = discardedMeals;
    this.totalCost = totalCost;
  }

  /**
   * Retrieves the list of discarded meals.
   *
   * @return A list of BuffetMealPortion representing the meals that were discarded.
   */
  public List<BuffetMealPortion> getDiscardedMeals() {
    return discardedMeals;
  }

  /**
   * Retrieves the total cost associated with the discarded meals.
   *
   * @return The total cost incurred due to the discarded meals.
   */
  public int getTotalCost() {
    return totalCost;
  }
}
