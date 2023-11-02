package com.codecool.ehotel.ui;

import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.service.buffet.BuffetManager;
import com.codecool.ehotel.service.buffet.BuffetMealPortion;
import com.codecool.ehotel.service.buffet.DiscardedMealsResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
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

  }

  public void printCycleStart(int cycle, LocalDateTime currentSimulatedTime) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
    System.out.println("\nStarting cycle: " + cycle + " - " + currentSimulatedTime.toLocalTime().format(timeFormatter) + "\n");
  }

  public void printMealsInTableFormat(List<String> consumedNames, List<String> consumedMeals, List<String> notConsumedNames, List<String> notConsumedMeals) {
    System.out.println("---------------------------------------------------------------------------------------------");
    System.out.format("| %-43s | %-43s |\n", "Consumed", "was not available");
    System.out.println("---------------------------------------------------------------------------------------------");
    System.out.format("| %-20s | %-20s | %-20s | %-20s |\n", "Name", "Meal", "Name", "Meal");
    System.out.println("---------------------------------------------------------------------------------------------");

    int maxRows = Math.max(consumedNames.size(), notConsumedNames.size());
    for (int i = 0; i < maxRows; i++) {
      String consumedName = (i < consumedNames.size()) ? consumedNames.get(i) : "";
      String consumedMeal = (i < consumedMeals.size()) ? consumedMeals.get(i) : "";
      String notConsumedName = (i < notConsumedNames.size()) ? notConsumedNames.get(i) : "";
      String notConsumedMeal = (i < notConsumedMeals.size()) ? notConsumedMeals.get(i) : "";

      System.out.format("| %-20s | %-20s | %-20s | %-20s |\n",
          consumedName,
          consumedMeal,
          notConsumedName,
          notConsumedMeal);
    }
    System.out.println("---------------------------------------------------------------------------------------------");
  }

  public void displayDiscardedMealsAndCost(MealDurability durability, DiscardedMealsResult discardedResult) {
    System.out.println("------------------------------------------------------------------");
    System.out.format("| %-20s | %-20s | %-16s |\n", "Discarded Meal" + durability, "Date", "Time");
    System.out.println("------------------------------------------------------------------");

    // sorting discarded meals by timestamps
    List<BuffetMealPortion> sortedMeals = new ArrayList<>(discardedResult.getDiscardedMeals());
    sortedMeals.sort(Comparator.comparing(BuffetMealPortion::getTimestamp));

    // iterating over sorted list to display
    for (BuffetMealPortion mealPortion : sortedMeals) {
      System.out.format("| %-20s | %-20s | %-16s |\n",
          mealPortion.getType().toString(),
          mealPortion.getTimestamp().toLocalDate(),
          mealPortion.getTimestamp().toLocalTime().truncatedTo(ChronoUnit.MINUTES));  // displaying time without seconds
    }

    System.out.println("------------------------------------------------------------------");
    System.out.println("Total Discarded Cost for " + durability + " durability meals: $" + discardedResult.getTotalCost());
    System.out.println("------------------------------------------------------------------");
  }



  public void printNoMealsToDiscardMessage(MealDurability durability) {
    System.out.println("No meals of durability " + durability.toString() + " to discard!");
  }
}
