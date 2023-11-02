package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.Map;

public interface BuffetService {

  void refillBuffet(BuffetManager buffetManager, Map<MealType, Integer> portionsToAdd, LocalDateTime currentSimulatedTime);

  boolean consumeFreshest(BuffetManager buffetManager, MealType type);

  DiscardedMealsResult collectWaste(BuffetManager buffetManager, MealDurability durability, LocalDateTime time);

}
