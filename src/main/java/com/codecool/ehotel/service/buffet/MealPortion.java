package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;

public class MealPortion {
  private MealType type;
  private LocalDateTime timestamp;

  public MealPortion(MealType type) {
    this.type = type;
    this.timestamp = LocalDateTime.now();
  }

  public Object getType() {
    return type;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  // for testing old meals
  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

}
