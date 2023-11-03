package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;

/* model of single meal portion
 *
 * holds information about the meal's type and the time it was prepared or added to the buffet
 *
 * 'type' property categorizes the meal
 * 'timestamp' property helps in identifying the freshness of meal
 */

public class BuffetMealPortion {
  private MealType type;
  private LocalDateTime timestamp;

  public BuffetMealPortion(MealType type, LocalDateTime timestamp) {
    this.type = type;
    this.timestamp = timestamp;
  }

  public MealType getType() {
    return type;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

}
