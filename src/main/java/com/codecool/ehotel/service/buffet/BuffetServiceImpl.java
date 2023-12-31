package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implements the BuffetService interface and provides higher-level operations related to a buffet.
 * This class is responsible for refilling the buffet, consuming the freshest meals, and collecting wasted meals.
 * It utilizes the BuffetManager to perform these operations.
 */
public class BuffetServiceImpl implements BuffetService {

  /**
   * Refills the buffet with a specified number of portions for each meal type.
   * This method adds new meal portions to the buffet as per the specified quantities.
   *
   * @param buffetManager The BuffetManager to manage the buffet.
   * @param portionsToAdd A map of MealType to Integer, specifying the number of portions to add for each meal type.
   * @param currentSimulatedTime The current simulated time in the system.
   */
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

  /**
   * Consumes the freshest meal portion of a specified type from the buffet.
   *
   * @param buffetManager The BuffetManager to manage the buffet.
   * @param type The MealType of the meal portion to consume.
   * @return True if the freshest meal portion was successfully consumed, false otherwise.
   */
  @Override
  public boolean consumeFreshest(BuffetManager buffetManager, MealType type) {
    return buffetManager.consumeFreshest(type);
  }

  /**
   * Collects wasted meals based on their durability and current time.
   * This method determines which meals should be discarded and calculates the total cost of discarded meals.
   *
   * @param buffetManager The BuffetManager to manage the buffet.
   * @param durability The MealDurability category to check for discarding meals.
   * @param currentTime The current time to determine if meals have expired.
   * @return A DiscardedMealsResult containing the list of discarded meals and their total cost.
   */
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

  /**
   * Determines if a meal type should be discarded based on its durability.
   *
   * @param type The MealType to check for discarding.
   * @param durability The MealDurability to compare with the meal type's durability.
   * @return True if the meal type's durability matches the specified durability, indicating it should be discarded.
   */
  private boolean shouldDiscardMealType(MealType type, MealDurability durability) {
    return type.getDurability() == durability;
  }

  /**
   * Discards expired meals from a list of meal portions and calculates the total cost of discarded meals.
   * This method iterates over the meal portions and removes the expired ones based on the current time,
   * adding their cost to the total cost accumulator.
   *
   * @param portions A list of BuffetMealPortion to check for expiration.
   * @param type The MealType of the meal portions.
   * @param currentTime The current time to determine if meals have expired.
   * @param discardedMeals A list to accumulate the discarded meal portions.
   * @return The total cost of the discarded meals.
   */
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

  /**
   * Determines if a buffet meal portion has expired based on the meal type's durability and the current time.
   * The expiration is assessed by comparing the meal portion's timestamp plus its durability duration with the current time.
   *
   * @param portion The BuffetMealPortion to check for expiration.
   * @param type The MealType of the meal portion.
   * @param currentTime The current time to compare against the meal portion's expiration time.
   * @return True if the meal portion is expired, false otherwise.
   */
  private boolean isPortionExpired(BuffetMealPortion portion, MealType type, LocalDateTime currentTime) {
    LocalDateTime discardTime = portion.getTimestamp().plusMinutes(type.getDurability().getDurationInMinutes());
    return discardTime.isBefore(currentTime);
  }
}

