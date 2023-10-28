package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.Map;

public interface BuffetService {
  BreakfastManager createSampleBuffet();

  void refillBuffet(BreakfastManager breakfastManager, Map<MealType, Integer> portionsToAdd);

  boolean consumeFreshest(BreakfastManager breakfastManager, MealType type);

  int collectWaste(BreakfastManager breakfastManager, MealDurability durability, LocalDateTime time);

}
