package com.codecool.ehotel.service.breakfast;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.*;
import com.codecool.ehotel.ui.DisplayBreakfast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreakfastManager {

  private BuffetService buffetService;
  private BuffetManager buffetManager;

  private DisplayBreakfast displayBreakfast;

  public BreakfastManager() {
    this.buffetService = new BuffetServiceImpl();
    this.buffetManager = new BuffetManager();
    this.displayBreakfast = new DisplayBreakfast();
  }

  public void serve() {

    displayBreakfast.initialGreeting();

    LocalTime cycleStartTime = LocalTime.of(6, 0);  // start at 6:00 AM
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");  // to format the time to 6:00 AM

    for (int cycle = 1; cycle <= 8; cycle++) {
      System.out.println("\nStarting cycle: " + cycle + " - " + cycleStartTime.format(timeFormatter) + "\n");

      // refill
      refillToMax();

      // display menu
      displayBreakfast.showBreakfastMenu(buffetManager);

      // consume
      // TODO: Implement consume logic here

      // discard
      // TODO: Implement discard logic here

      cycleStartTime = cycleStartTime.plusMinutes(30);  // move to the next half hour for the next cycle
    }

  }

  private void setupInitialBuffet() {
    //buffetManager = buffetService.createInitialBuffet();
    refillToMax();
    displayBreakfast.initialGreeting();
    displayBreakfast.showBreakfastMenu(buffetManager);
  }

  public void refillToMax() {
    Map<MealType, Integer> refillMap = new HashMap<>();

    // determine for each MealType how many portions are missing && put them in map
    for (MealType type : MealType.values()) {
      int currentCount = buffetManager.getCountOfMealType(type);
      int missingCount = 3 - currentCount; // determine how many are missing to reach max

      if (missingCount > 0) {
        refillMap.put(type, missingCount); // add missing count for current type
      }
    }

    // refill the buffet using the refillMap
    buffetService.refillBuffet(buffetManager, refillMap);
  }

 /* private static void testingConsumeMeal(BuffetManager buffetManager) {
    buffetService.consumeFreshest(buffetManager, MealType.SCRAMBLED_EGGS);
    System.out.println("\nBuffet after consumption:");
    display.showBreakfastMenu(buffetManager);
  }

  private static void testingRefillBuffet(BuffetManager buffetManager) {
    Map<MealType, Integer> refillSpec = BuffetRefillSpecification.getSampleRefillSpecification();
    buffetService.refillBuffet(buffetManager, refillSpec);
  }

  private static void simulateOldMeals(BuffetManager buffetManager) {
    List<BuffetMealPortion> allMeals = buffetManager.getAllMeals();

    for (int i = 0; i < allMeals.size() / 2; i++) { // make half of the meals a day older
      allMeals.get(i).setTimestamp(allMeals.get(i).getTimestamp().minusDays(1));
    }
  }

  private static void simulateWasteCollection(BuffetManager buffetManager) {
    // Simulating scenario to remove all SHORT durability meals that were added before today's date
    int wastedCost = buffetService.collectWaste(buffetManager, MealDurability.SHORT, LocalDate.now().atStartOfDay());
    System.out.println("\nCost of wasted meals: $" + wastedCost);
  }

  private static void displayUpdatedBuffet(BuffetManager buffetManager) {
    System.out.println("\nUpdated Breakfast Buffet:");
    display.showBreakfastMenu(buffetManager);
  }*/
}
