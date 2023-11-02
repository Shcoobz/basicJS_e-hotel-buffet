package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
  public void refillBuffet(BuffetManager buffetManager, Map<MealType, Integer> portionsToAdd, LocalDateTime currentSimulatedTime) {
    for (Map.Entry<MealType, Integer> entry : portionsToAdd.entrySet()) {
      MealType type = entry.getKey();
      int count = entry.getValue();

      for (int i = 0; i < count; i++) {
        buffetManager.addMealPortion(new BuffetMealPortion(type, currentSimulatedTime));
      }
    }
  }

  @Override
  public boolean consumeFreshest(BuffetManager buffetManager, MealType type) {
    return buffetManager.consumeFreshest(type);
  }

  @Override
  public DiscardedMealsResult collectWaste(BuffetManager buffetManager, MealDurability durability, LocalDateTime currentTime) {
    List<BuffetMealPortion> discardedMeals = new ArrayList<>();
    int totalCost = 0;

    for (MealType type : MealType.values()) {

      // check if durability matches provided durability
      if (type.getDurability() == durability) {

        // get all meals of type
        List<BuffetMealPortion> portions = buffetManager.getAllMealsOfType(type);

        // loop in reverse order to avoid shifting issues when removing
        for (int i = portions.size() - 1; i >= 0; i--) {
          BuffetMealPortion portion = portions.get(i);

          // if portion's timestamp plus durability duration is before the current time, discard it
          LocalDateTime discardTime = portion.getTimestamp().plusMinutes(type.getDurability().getDurationInMinutes());
          if (discardTime.isBefore(currentTime)) {
            totalCost += type.getCost();
            discardedMeals.add(portion);
            portions.remove(i);
          }
        }
      }
    }

    return new DiscardedMealsResult(discardedMeals, totalCost);
  }


}

