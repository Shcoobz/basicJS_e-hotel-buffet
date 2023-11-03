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
      if (shouldDiscardMealType(type, durability)) {
        List<BuffetMealPortion> portions = buffetManager.getAllMealsOfType(type);
        totalCost += discardExpiredMeals(portions, type, currentTime, discardedMeals);
      }
    }

    return new DiscardedMealsResult(discardedMeals, totalCost);
  }

  private boolean shouldDiscardMealType(MealType type, MealDurability durability) {
    return type.getDurability() == durability;
  }

  private int discardExpiredMeals(List<BuffetMealPortion> portions, MealType type, LocalDateTime currentTime, List<BuffetMealPortion> discardedMeals) {
    int costAccumulator = 0;

    for (int i = portions.size() - 1; i >= 0; i--) {
      BuffetMealPortion portion = portions.get(i);
      if (isPortionExpired(portion, type, currentTime)) {
        costAccumulator += type.getCost();
        discardedMeals.add(portion);
        portions.remove(i);
      }
    }

    return costAccumulator;
  }

  private boolean isPortionExpired(BuffetMealPortion portion, MealType type, LocalDateTime currentTime) {
    LocalDateTime discardTime = portion.getTimestamp().plusMinutes(type.getDurability().getDurationInMinutes());
    return discardTime.isBefore(currentTime);
  }

}

