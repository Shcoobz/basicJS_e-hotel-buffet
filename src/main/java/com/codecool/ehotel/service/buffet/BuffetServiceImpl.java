package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
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
  public BreakfastManager createSampleBuffet() {
    BreakfastManager breakfastManager = new BreakfastManager();

/*    // testing
    buffetManager.addMealPortion(new MealPortion(MealType.SCRAMBLED_EGGS));
    buffetManager.addMealPortion(new MealPortion(MealType.SUNNY_SIDE_UP));
    buffetManager.addMealPortion(new MealPortion(MealType.CROISSANT));*/

    // generate each meal once
    for (MealType type : MealType.values()) {
      breakfastManager.addMealPortion(new MealPortion(type));
    }

    return breakfastManager;
  }

  @Override
  public void refillBuffet(BreakfastManager breakfastManager, Map<MealType, Integer> portionsToAdd) {
    for (Map.Entry<MealType, Integer> entry : portionsToAdd.entrySet()) {
      MealType type = entry.getKey();
      int count = entry.getValue();

      for (int i = 0; i < count; i++) {
        breakfastManager.addMealPortion(new MealPortion(type));
      }
    }
  }

  @Override
  public boolean consumeFreshest(BreakfastManager breakfastManager, MealType type) {
    return breakfastManager.consumeFreshest(type);
  }

  @Override
  public int collectWaste(BreakfastManager breakfastManager, MealDurability durability, LocalDateTime time) {
    int totalCost = 0;

    // loop over all MealTypes
    for (MealType type : MealType.values()) {

      // check if durability matches provided durability
      if (type.getDurability() == durability) {

        // get all meals of this type
        List<MealPortion> portions = breakfastManager.getAllMealsOfType(type);

        // loop in reverse order to avoid shifting issues when removing
        for (int i = portions.size() - 1; i >= 0; i--) {
          MealPortion portion = portions.get(i);

          // if portion's timestamp is before given time, discard it
          if (portion.getTimestamp().isBefore(time)) {
            totalCost += type.getCost();
            portions.remove(i);
          }
        }
      }
    }

    return totalCost;
  }

}

