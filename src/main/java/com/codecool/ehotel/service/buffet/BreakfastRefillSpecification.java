package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.util.HashMap;
import java.util.Map;

public class BreakfastRefillSpecification {

  // testing
  public static Map<MealType, Integer> getSampleRefillSpecification() {
    Map<MealType, Integer> portionsToAdd = new HashMap<>();

    portionsToAdd.put(MealType.SCRAMBLED_EGGS, 5);
    portionsToAdd.put(MealType.SUNNY_SIDE_UP, 3);
    portionsToAdd.put(MealType.CROISSANT, 10);

    return portionsToAdd;
  }
}
