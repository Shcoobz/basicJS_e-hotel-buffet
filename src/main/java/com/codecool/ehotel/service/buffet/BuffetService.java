package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.Map;

public interface BuffetService {
  BuffetManager createInitialBuffet();

  void refillBuffet(BuffetManager buffetManager, Map<MealType, Integer> portionsToAdd);

  boolean consumeFreshest(BuffetManager buffetManager, MealType type);

  int collectWaste(BuffetManager buffetManager, MealDurability durability, LocalDateTime time);

}
