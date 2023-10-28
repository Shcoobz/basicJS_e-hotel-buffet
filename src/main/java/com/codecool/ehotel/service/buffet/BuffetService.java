package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.util.Map;

public interface BuffetService {
  BuffetManager createSampleBuffet();
  void refillBuffet(BuffetManager buffetManager, Map<MealType, Integer> portionsToAdd);
}
