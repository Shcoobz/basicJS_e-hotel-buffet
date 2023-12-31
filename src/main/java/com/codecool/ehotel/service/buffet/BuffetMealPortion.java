package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;

/**
 * Represents a single portion of a meal in the buffet.
 * This class holds information about the meal's type and the time it was prepared or added to the buffet.
 * It is used to manage and track individual meal portions in the buffet.
 */
public class BuffetMealPortion {
  private MealType type;
  private LocalDateTime timestamp;

  /**
   * Constructs a BuffetMealPortion with a specific type and timestamp.
   *
   * @param type The type of the meal, defining its category.
   * @param timestamp The time when the meal was prepared or added to the buffet.
   */
  public BuffetMealPortion(MealType type, LocalDateTime timestamp) {
    this.type = type;
    this.timestamp = timestamp;
  }

  /**
   * Returns the type of the meal.
   *
   * @return The MealType representing the category of the meal.
   */
  public MealType getType() {
    return type;
  }

  /**
   * Returns the timestamp when the meal was added to the buffet.
   *
   * @return The LocalDateTime representing the time the meal was prepared or added.
   */
  public LocalDateTime getTimestamp() {
    return timestamp;
  }

}
