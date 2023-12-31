package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;
import java.util.*;

/**
 * Manages the meals at a buffet. This class is responsible for tracking meals, their types, and quantities.
 * It provides methods to interact with this data, such as adding a meal portion, getting the freshest meal,
 * consuming the freshest meal, and querying meals by type.
 */
public class BuffetManager {

  private Map<MealType, List<BuffetMealPortion>> meals;

  /**
   * Constructs a BuffetManager instance and initializes the meal storage.
   * Each meal type is associated with an empty list of buffet meal portions.
   */
  public BuffetManager() {
    this.meals = new HashMap<>();

    for (MealType type : MealType.values()) {
      this.meals.put(type, new ArrayList<>());
    }
  }

  /**
   * Adds a meal portion to the buffet.
   *
   * @param portion The BuffetMealPortion to be added.
   */
  public void addMealPortion(BuffetMealPortion portion) {
    meals.get(portion.getType()).add(portion);
  }

  /**
   * Retrieves all meal portions available at the buffet.
   *
   * @return A list of all BuffetMealPortions.
   */
  public List<BuffetMealPortion> getAllMeals() {
    List<BuffetMealPortion> allMeals = new ArrayList<>();
    for (List<BuffetMealPortion> queue : meals.values()) {
      allMeals.addAll(queue);
    }
    return allMeals;
  }

  /**
   * Retrieves the freshest portion of a specified meal type.
   *
   * @param type The MealType for which the freshest portion is requested.
   * @return The freshest BuffetMealPortion of the specified type, or null if none available.
   */
  public BuffetMealPortion getFreshestPortion(MealType type) {
    List<BuffetMealPortion> buffetMealPortions = meals.get(type);
    if (buffetMealPortions != null && !buffetMealPortions.isEmpty()) {
      return buffetMealPortions.get(buffetMealPortions.size() - 1);
    }
    return null;
  }

  /**
   * Consumes the freshest meal portion of a specified type.
   *
   * @param type The MealType for which the freshest portion is to be consumed.
   * @return True if the freshest portion was successfully consumed, false otherwise.
   */
  public boolean consumeFreshest(MealType type) {
    List<BuffetMealPortion> buffetMealPortions = meals.get(type);
    if (buffetMealPortions != null && !buffetMealPortions.isEmpty()) {
      BuffetMealPortion freshestPortion = getFreshestPortion(type);
      return buffetMealPortions.remove(freshestPortion);
    }
    return false;
  }

  /**
   * Retrieves all meal portions of a specific type.
   *
   * @param type The MealType for which meal portions are requested.
   * @return A list of BuffetMealPortions of the specified type.
   */
  public List<BuffetMealPortion> getAllMealsOfType(MealType type) {
    return meals.getOrDefault(type, new ArrayList<>());
  }

  /**
   * Gets the count of meal portions available for a specific type.
   *
   * @param type The MealType for which the count is requested.
   * @return The number of meal portions of the specified type.
   */
  public int getCountOfMealType(MealType type) {
    return (int) getAllMeals().stream().filter(meal -> meal.getType() == type).count();

  }
}
