package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealType;

import java.util.Map;

/* business logic class // controller
  *
  * implements BuffetService interface
  * provides higher-level set of operations related to buffet
  * creating sample buffet, refilling the buffet
  *
  * methods in this class use BuffetManager to perform their operations
  * refillBuffet method uses the BuffetManager's addMealPortion method
*/

public class BuffetServiceImpl implements BuffetService {

  @Override
  public BuffetManager createSampleBuffet() {
    BuffetManager buffetManager = new BuffetManager();

/*    // testing
    buffetManager.addMealPortion(new MealPortion(MealType.SCRAMBLED_EGGS));
    buffetManager.addMealPortion(new MealPortion(MealType.SUNNY_SIDE_UP));
    buffetManager.addMealPortion(new MealPortion(MealType.CROISSANT));*/

    // generate each meal once
    for (MealType type : MealType.values()) {
      buffetManager.addMealPortion(new MealPortion(type));
    }

    return buffetManager;
  }

  @Override
  public void refillBuffet(BuffetManager buffetManager, Map<MealType, Integer> portionsToAdd) {
    for (Map.Entry<MealType, Integer> entry : portionsToAdd.entrySet()) {
      MealType type = entry.getKey();
      int count = entry.getValue();

      for (int i = 0; i < count; i++) {
        buffetManager.addMealPortion(new MealPortion(type));
      }
    }
  }
}

