package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

public class BuffetServiceImpl implements BuffetService {
  @Override
  public BuffetManager createSampleBuffet() {
    BuffetManager buffetManager = new BuffetManager();

    buffetManager.addMealPortion(new MealPortion(MealType.SCRAMBLED_EGGS));
    buffetManager.addMealPortion(new MealPortion(MealType.SUNNY_SIDE_UP));
    buffetManager.addMealPortion(new MealPortion(MealType.CROISSANT));

    return buffetManager;

  }
}

