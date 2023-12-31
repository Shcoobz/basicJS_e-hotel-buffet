package com.codecool.ehotel.service.buffet;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Interface defining the services related to the management of a buffet.
 * This includes refilling the buffet, consuming meals, and collecting waste.
 */
public interface BuffetService {

  /**
   * Refills the buffet with specified quantities of different meal types.
   * The method adds portions of meals to the buffet as per the provided map, using the current simulated time.
   *
   * @param buffetManager The manager responsible for the buffet's operations.
   * @param portionsToAdd A map containing meal types and their respective quantities to add.
   * @param currentSimulatedTime The current simulated time for timestamping the added meals.
   */
  void refillBuffet(BuffetManager buffetManager, Map<MealType, Integer> portionsToAdd, LocalDateTime currentSimulatedTime);

  /**
   * Consumes the freshest available portion of a specified meal type from the buffet.
   *
   * @param buffetManager The manager responsible for the buffet's operations.
   * @param type The type of the meal to consume.
   * @return True if a meal of the specified type was successfully consumed, false otherwise.
   */
  boolean consumeFreshest(BuffetManager buffetManager, MealType type);

  /**
   * Collects and discards waste from the buffet based on meal durability and the current time.
   * This method returns the result containing discarded meals and their total cost.
   *
   * @param buffetManager The manager responsible for the buffet's operations.
   * @param durability The durability category of the meals to be checked for discarding.
   * @param time The current time to determine if meals are expired and should be discarded.
   * @return A DiscardedMealsResult object containing the list of discarded meals and their total cost.
   */
  DiscardedMealsResult collectWaste(BuffetManager buffetManager, MealDurability durability, LocalDateTime time);

}
