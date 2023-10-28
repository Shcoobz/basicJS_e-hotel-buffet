package com.codecool.ehotel.ui;

import com.codecool.ehotel.service.buffet.BuffetManager;
import com.codecool.ehotel.service.buffet.BuffetMealPortion;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class DisplayBreakfast {

  public void initialGreeting() {
    System.out.println("\n*** Initial setup ***");
    System.out.println("\nGood Morning, beloved guests! \nEnjoy your breakfast menu:");
  }

  public void showBreakfastMenu(BuffetManager buffetManager) {
    List<BuffetMealPortion> allMeals = buffetManager.getAllMeals();

    System.out.println("------------------------------------------------------------------");
    System.out.format("| %-20s | %-20s | %-16s |\n", "Meal", "Date", "Time");
    System.out.println("------------------------------------------------------------------");

    for (BuffetMealPortion meal : allMeals) {
      System.out.format("| %-20s | %-20s | %-16s |\n",
          meal.getType(),
          meal.getTimestamp().toLocalDate(),
          meal.getTimestamp().toLocalTime().truncatedTo(ChronoUnit.MINUTES));  // displaying time without seconds
    }
    System.out.println("------------------------------------------------------------------");

    /*MealPortion freshestScrambledEggs = buffetManager.getFreshestPortion(MealType.SCRAMBLED_EGGS);
    System.out.println("\nFreshest scrambled eggs prepared at: " + freshestScrambledEggs.getTimestamp());

    MealPortion leastFreshCroissant = buffetManager.getLeastFreshPortion(MealType.CROISSANT);
    System.out.println("Oldest croissant prepared at: " + leastFreshCroissant.getTimestamp());*/
  }
}
