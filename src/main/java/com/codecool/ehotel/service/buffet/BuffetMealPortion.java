package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;

public class BuffetMealPortion {
  private MealType type;
  private LocalDateTime timestamp;

  public BuffetMealPortion(MealType type, LocalDateTime timestamp) {
    this.type = type;
    this.timestamp = timestamp;
  }

  /*public MealType getType() {
    return type;
  }*/

  public MealType getType() {
    return type;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }


}
