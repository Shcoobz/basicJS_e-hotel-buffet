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
  private static final String LINE_SEPARATOR = "-------------------------------------------------------------------------------------------------------------------------------------------";

  public void initialGreeting() {
    System.out.println("\n*** Initial setup ***");
    System.out.println("\nGood Morning, beloved guests! \nEnjoy your breakfast menu:");
  }

  public void stageEndOfDayDiscard() {
    System.out.println("\nStage: End of Day Discard");
  }

  public void printCycleStart(int cycle, LocalDateTime currentSimulatedTime) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
    System.out.println("\nStarting cycle: " + cycle + " - " + currentSimulatedTime.toLocalTime().format(timeFormatter) + "\n");
  }

  // buffet menu
  public static void printMenuHeader() {
    System.out.println("Stage: Refill \n");

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-43s | %-43s | %-43s |\n", "Meal", "Date", "Time");
    System.out.println(LINE_SEPARATOR);
  }

  public static void printMenuDetails(List<BuffetMealPortion> allMeals) {
    for (BuffetMealPortion meal : allMeals) {
      System.out.format("| %-43s | %-43s | %-43s |\n",
          meal.getType(),
          meal.getTimestamp().toLocalDate(),
          meal.getTimestamp().toLocalTime().truncatedTo(ChronoUnit.MINUTES));  // displaying time without seconds
    }
  }

  public static void printMenuFooter() {
    System.out.println(LINE_SEPARATOR);
  }

  public void showBreakfastMenu(BuffetManager buffetManager) {
    List<BuffetMealPortion> allMeals = buffetManager.getAllMeals();

    printMenuHeader();
    printMenuDetails(allMeals);
    printMenuFooter();
  }

  // consume
  public static void printConsumeHeader() {
    System.out.println("\nStage: Consume\n");

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-69s | %-63s |\n", "Consumed", "was not available");
    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-33s | %-33s | %-33s | %-27s |\n", "Name", "Meal", "Name", "Meal");
    System.out.println(LINE_SEPARATOR);
  }

  public static void printConsumeDetails(List<String> consumedNames, List<String> consumedMeals, List<String> notConsumedNames, List<String> notConsumedMeals) {
    int maxRows = Math.max(consumedNames.size(), notConsumedNames.size());
    for (int i = 0; i < maxRows; i++) {
      String consumedName = (i < consumedNames.size()) ? consumedNames.get(i) : "";
      String consumedMeal = (i < consumedMeals.size()) ? consumedMeals.get(i) : "";
      String notConsumedName = (i < notConsumedNames.size()) ? notConsumedNames.get(i) : "";
      String notConsumedMeal = (i < notConsumedMeals.size()) ? notConsumedMeals.get(i) : "";

      System.out.format("| %-33s | %-33s | %-33s | %-27s |\n",
          consumedName,
          consumedMeal,
          notConsumedName,
          notConsumedMeal);
    }
  }

  public static void printConsumeFooter() {
    System.out.println(LINE_SEPARATOR);
  }

  public void showConsumedMeals(List<String> consumedNames, List<String> consumedMeals, List<String> notConsumedNames, List<String> notConsumedMeals) {
    printConsumeHeader();
    printConsumeDetails(consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);
    printConsumeFooter();
  }

  // discard
  public static void printDiscardHeader(MealDurability durabilityShort, MealDurability durabilityMedium) {
    System.out.println("\nStage: Discard\n");

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n", durabilityShort, "Date", "Time", durabilityMedium, "Date", "Time");
    System.out.println(LINE_SEPARATOR);
  }

  public static String[] getSortedMealInfoShort(List<BuffetMealPortion> sortedMealsShort, int index) {
    String[] info = new String[3]; // [meal, date, time]

    if (index == 0 && sortedMealsShort.isEmpty()) {
      info[0] = "No meals to discard";
      info[1] = "";
      info[2] = "";
    } else if (index < sortedMealsShort.size()) {
      BuffetMealPortion meal = sortedMealsShort.get(index);
      info[0] = meal.getType().toString();
      info[1] = meal.getTimestamp().toLocalDate().toString();
      info[2] = meal.getTimestamp().toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString();
    }

    return info;
  }

  public static String[] getSortedMealInfoMedium(List<BuffetMealPortion> sortedMealsMedium, int index) {
    String[] info = new String[3]; // [meal, date, time]

    if (index == 0 && sortedMealsMedium.isEmpty()) {
      info[0] = "No meals to discard";
      info[1] = "";
      info[2] = "";
    } else if (index < sortedMealsMedium.size()) {
      BuffetMealPortion meal = sortedMealsMedium.get(index);
      info[0] = meal.getType().toString();
      info[1] = meal.getTimestamp().toLocalDate().toString();
      info[2] = meal.getTimestamp().toLocalTime().truncatedTo(ChronoUnit.MINUTES).toString();
    } else {
      // When index is beyond the list size, return an array with empty strings
      info[0] = "";
      info[1] = "";
      info[2] = "";
    }

    return info;
  }

  private void printDiscardDetails(DiscardedMealsResult discardedResultShort, DiscardedMealsResult discardedResultMedium) {
    List<BuffetMealPortion> sortedMealsShort = sortMealsByTimestamp(discardedResultShort);
    List<BuffetMealPortion> sortedMealsMedium = sortMealsByTimestamp(discardedResultMedium);

    if (sortedMealsShort.isEmpty() && sortedMealsMedium.isEmpty()) {
      System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
          "No meals to discard", "", "",
          "No meals to discard", "", "");
    } else {
      int maxRows = Math.max(sortedMealsShort.size(), sortedMealsMedium.size());

      for (int i = 0; i < maxRows; i++) {
        String[] infoShort = getSortedMealInfoShort(sortedMealsShort, i);
        String[] infoMedium = getSortedMealInfoMedium(sortedMealsMedium, i);

        System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n",
            infoShort[0], infoShort[1], infoShort[2],
            infoMedium[0], infoMedium[1], infoMedium[2]);
      }
    }
  }

  private List<BuffetMealPortion> sortMealsByTimestamp(DiscardedMealsResult discardedResult) {
    List<BuffetMealPortion> sortedMeals = new ArrayList<>(discardedResult.getDiscardedMeals());
    sortedMeals.sort(Comparator.comparing(BuffetMealPortion::getTimestamp));
    return sortedMeals;
  }

  private int calculateTotalCosts(DiscardedMealsResult... results) {
    int total = 0;
    for (DiscardedMealsResult result : results) {
      total += result.getTotalCost();
    }
    return total;
  }

  public void printDiscardFooter(MealDurability durabilityShort, DiscardedMealsResult discardedResultShort, MealDurability durabilityMedium, DiscardedMealsResult discardedResultMedium) {
    int totalCosts = calculateTotalCosts(discardedResultShort, discardedResultMedium);

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-45s $%-88d |\n", "Total Costs for " + durabilityShort + " durability meals:", discardedResultShort.getTotalCost());
    System.out.format("| %-45s $%-88d |\n", "Total Cost for " + durabilityMedium + " durability meals:", discardedResultMedium.getTotalCost());
    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-45s $%-88d |\n", "Total Cost for this cycle:", totalCosts);
    System.out.println(LINE_SEPARATOR);
  }

  public void displayDiscardedMealsAndCostSideBySide(MealDurability durabilityShort, DiscardedMealsResult discardedResultShort, MealDurability durabilityMedium, DiscardedMealsResult discardedResultMedium) {
    printDiscardHeader(durabilityShort, durabilityMedium);
    printDiscardDetails(discardedResultShort, discardedResultMedium);
    printDiscardFooter(durabilityShort, discardedResultShort, durabilityMedium, discardedResultMedium);
  }

  public void displaySessionMetrics(int totalUnhappyGuests, double totalWasteCost) {
    System.out.println("\nSession Metrics:");
    System.out.println("Total Unhappy Guests: " + totalUnhappyGuests);
    System.out.println("Total Waste Cost: $" + totalWasteCost);
  }
}
