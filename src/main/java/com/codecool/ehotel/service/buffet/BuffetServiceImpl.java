package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
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

/*  @Override
  public BuffetManager createInitialBuffet() {
    BuffetManager buffetManager = new BuffetManager();

    // setup first breakfast buffet: generates 3 meals of each meal type
    for (MealType type : MealType.values()) {
      for (int i = 0; i < 3; i++) {
        buffetManager.addMealPortion(new BuffetMealPortion(type));
      }
    }

    return buffetManager;
  }*/

  @Override
  public void refillBuffet(BuffetManager buffetManager, Map<MealType, Integer> portionsToAdd) {
    for (Map.Entry<MealType, Integer> entry : portionsToAdd.entrySet()) {
      MealType type = entry.getKey();
      int count = entry.getValue();

      for (int i = 0; i < count; i++) {
        buffetManager.addMealPortion(new BuffetMealPortion(type));
      }
    }
  }

  @Override
  public boolean consumeFreshest(BuffetManager buffetManager, MealType type) {
    return buffetManager.consumeFreshest(type);
  }

  @Override
  public int collectWaste(BuffetManager buffetManager, MealDurability durability, LocalDateTime time) {
    int totalCost = 0;

    // loop over all MealTypes
    for (MealType type : MealType.values()) {

      // check if durability matches provided durability
      if (type.getDurability() == durability) {

        // get all meals of this type
        List<BuffetMealPortion> portions = buffetManager.getAllMealsOfType(type);

        // loop in reverse order to avoid shifting issues when removing
        for (int i = portions.size() - 1; i >= 0; i--) {
          BuffetMealPortion portion = portions.get(i);

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

