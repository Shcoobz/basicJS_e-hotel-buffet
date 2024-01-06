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

/**
 * Provides methods for displaying various aspects of a hotel's breakfast service.
 * This includes printing the logo, initial greeting, menu details, consumption details, and discarded meals.
 */
public class DisplayBreakfast {
  private static final String LINE_SEPARATOR = "-------------------------------------------------------------------------------------------------------------------------------------------";

  /**
   * Prints the logo of the breakfast service.
   */
  public void printLogo() {
    System.out.println("           _               _            _                    ");
    System.out.println("          | |             | |          | |                   ");
    System.out.println("     ___  | |__     ___   | |_    ___  | |                   ");
    System.out.println("    / _ \\ | '_ \\   / _ \\  | __|  / _ \\ | |                   ");
    System.out.println("   |  __/ | | | | | (_) | | |_  |  __/ | |                   ");
    System.out.println("    \\___| |_| |_|  \\___/   \\__|  \\___| |_|                   ");
    System.out.println("                                                              ");
    System.out.println("                                                              ");
    System.out.println("                      ____             __    __          _   ");
    System.out.println("                     |  _ \\           / _|  / _|        | |  ");
    System.out.println("                     | |_) |  _   _  | |_  | |_    ___  | |_ ");
    System.out.println("                     |  _ <  | | | | |  _| |  _|  / _ \\ | __|");
    System.out.println("                     | |_) | | |_| | | |   | |   |  __/ | |_ ");
    System.out.println("                     |____/   \\__,_| |_|   |_|    \\___|  \\__|");
    System.out.println("                                                              ");
    System.out.println("                                                              ");
  }

  /**
   * Prints the initial greeting message for the breakfast service.
   */
  public void initialGreeting() {
    System.out.println("\n*** Initial setup ***");
    System.out.println("\nGood Morning, beloved guests! \nEnjoy your breakfast menu :)");
  }

  /**
   * Indicates the end of the day discard stage.
   */
  public void stageEndOfDayDiscard() {
    System.out.println("\nStage: End of Day Discard");
  }

  /**
   * Prints the start of a breakfast cycle with its number and current time.
   *
   * @param cycle The cycle number.
   * @param currentSimulatedTime The current simulated time.
   */
  public void printCycleStart(int cycle, LocalDateTime currentSimulatedTime) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
    System.out.println("\nStarting cycle: " + cycle + " - " + currentSimulatedTime.toLocalTime().format(timeFormatter) + "\n");
  }

  /**
   * Prints the header for the breakfast menu.
   */
  public static void printMenuHeader() {
    System.out.println("Stage: Refill \n");

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-43s | %-43s | %-43s |\n", "Meal", "Date", "Time");
    System.out.println(LINE_SEPARATOR);
  }

  /**
   * Prints the details of the breakfast menu.
   *
   * @param allMeals The list of all meals to be displayed.
   */
  public static void printMenuDetails(List<BuffetMealPortion> allMeals) {
    for (BuffetMealPortion meal : allMeals) {
      System.out.format("| %-43s | %-43s | %-43s |\n",
          meal.getType(),
          meal.getTimestamp().toLocalDate(),
          meal.getTimestamp().toLocalTime().truncatedTo(ChronoUnit.MINUTES));
    }
  }

  /**
   * Prints the footer for the breakfast menu.
   */
  public static void printMenuFooter() {
    System.out.println(LINE_SEPARATOR);
  }

  /**
   * Displays the breakfast menu using the buffet manager.
   *
   * @param buffetManager The buffet manager to manage the breakfast menu.
   */
  public void showBreakfastMenu(BuffetManager buffetManager) {
    List<BuffetMealPortion> allMeals = buffetManager.getAllMeals();

    printMenuHeader();
    printMenuDetails(allMeals);
    printMenuFooter();
  }

  /**
   * Prints the header for the consumption stage in the breakfast service.
   * This header includes columns for both consumed meals and meals that were not available.
   */
  public static void printConsumeHeader() {
    System.out.println("\nStage: Consume\n");

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-69s | %-63s |\n", "Consumed", "was not available");
    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-33s | %-33s | %-33s | %-27s |\n", "Name", "Meal", "Name", "Meal");
    System.out.println(LINE_SEPARATOR);
  }

  /**
   * Prints the details of consumed and not consumed meals.
   * This method formats and displays the names of guests and the meals they consumed or attempted to consume.
   *
   * @param consumedNames List of names of guests who successfully consumed meals.
   * @param consumedMeals List of meals that were successfully consumed.
   * @param notConsumedNames List of names of guests who could not consume meals.
   * @param notConsumedMeals List of meals that were not available for consumption.
   */
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

  /**
   * Prints the footer for the consumption stage.
   */
  public static void printConsumeFooter() {
    System.out.println(LINE_SEPARATOR);
  }

  /**
   * Displays the details of consumed and not consumed meals.
   * This method aggregates the information related to meal consumption and prints it in a formatted manner.
   *
   * @param consumedNames List of names of guests who successfully consumed meals.
   * @param consumedMeals List of meals that were successfully consumed.
   * @param notConsumedNames List of names of guests who could not consume meals.
   * @param notConsumedMeals List of meals that were not available for consumption.
   */
  public void showConsumedMeals(List<String> consumedNames, List<String> consumedMeals, List<String> notConsumedNames, List<String> notConsumedMeals) {
    printConsumeHeader();
    printConsumeDetails(consumedNames, consumedMeals, notConsumedNames, notConsumedMeals);
    printConsumeFooter();
  }

  /**
   * Prints the header for the discard stage in the breakfast service.
   * This header includes information about meal durability and timing for the meals to be discarded.
   *
   * @param durabilityShort The short durability category.
   * @param durabilityMedium The medium durability category.
   */
  public static void printDiscardHeader(MealDurability durabilityShort, MealDurability durabilityMedium) {
    System.out.println("\nStage: Discard\n");

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-20s | %-20s | %-20s | %-20s | %-20s | %-20s |\n", durabilityShort, "Date", "Time", durabilityMedium, "Date", "Time");
    System.out.println(LINE_SEPARATOR);
  }

  /**
   * Retrieves the meal information for meals with short durability.
   * Returns an array containing the meal type, date, and time for a specified index in the sorted meals list.
   *
   * @param sortedMealsShort The list of sorted meals with short durability.
   * @param index The index of the meal in the list.
   * @return An array of strings containing the meal's type, date, and time.
   */
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

  /**
   * Retrieves the meal information for meals with medium durability.
   * Returns an array containing the meal type, date, and time for a specified index in the sorted meals list.
   *
   * @param sortedMealsMedium The list of sorted meals with medium durability.
   * @param index The index of the meal in the list.
   * @return An array of strings containing the meal's type, date, and time.
   */
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
      info[0] = "";
      info[1] = "";
      info[2] = "";
    }
    return info;
  }

  /**
   * Prints the detailed information of discarded meals.
   * This includes sorting the meals by timestamp and displaying them in a formatted manner.
   *
   * @param discardedResultShort The result containing discarded meals with short durability.
   * @param discardedResultMedium The result containing discarded meals with medium durability.
   */
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

  /**
   * Sorts meals by their timestamp.
   *
   * @param discardedResult The result containing discarded meals.
   * @return A list of sorted BuffetMealPortions based on their timestamp.
   */
  private List<BuffetMealPortion> sortMealsByTimestamp(DiscardedMealsResult discardedResult) {
    List<BuffetMealPortion> sortedMeals = new ArrayList<>(discardedResult.getDiscardedMeals());
    sortedMeals.sort(Comparator.comparing(BuffetMealPortion::getTimestamp));
    return sortedMeals;
  }

  /**
   * Calculates the total costs of discarded meals from multiple results.
   *
   * @param results An array of DiscardedMealsResult.
   * @return The total cost of all discarded meals.
   */
  private int calculateTotalCosts(DiscardedMealsResult... results) {
    int total = 0;
    for (DiscardedMealsResult result : results) {
      total += result.getTotalCost();
    }
    return total;
  }

  /**
   * Prints the footer for the discard stage, including total costs for different meal durability categories.
   *
   * @param durabilityShort The short meal durability category.
   * @param discardedResultShort The result containing discarded meals with short durability.
   * @param durabilityMedium The medium meal durability category.
   * @param discardedResultMedium The result containing discarded meals with medium durability.
   */
  public void printDiscardFooter(MealDurability durabilityShort, DiscardedMealsResult discardedResultShort, MealDurability durabilityMedium, DiscardedMealsResult discardedResultMedium) {
    int totalCosts = calculateTotalCosts(discardedResultShort, discardedResultMedium);

    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-45s $%-88d |\n", "Total Costs for " + durabilityShort + " durability meals:", discardedResultShort.getTotalCost());
    System.out.format("| %-45s $%-88d |\n", "Total Cost for " + durabilityMedium + " durability meals:", discardedResultMedium.getTotalCost());
    System.out.println(LINE_SEPARATOR);
    System.out.format("| %-45s $%-88d |\n", "Total Cost for this cycle:", totalCosts);
    System.out.println(LINE_SEPARATOR);
  }

  /**
   * Displays discarded meals and their costs side by side for different durability categories.
   *
   * @param durabilityShort The short meal durability category.
   * @param discardedResultShort The result containing discarded meals with short durability.
   * @param durabilityMedium The medium meal durability category.
   * @param discardedResultMedium The result containing discarded meals with medium durability.
   */
  public void displayDiscardedMealsAndCostSideBySide(MealDurability durabilityShort, DiscardedMealsResult discardedResultShort, MealDurability durabilityMedium, DiscardedMealsResult discardedResultMedium) {
    printDiscardHeader(durabilityShort, durabilityMedium);
    printDiscardDetails(discardedResultShort, discardedResultMedium);
    printDiscardFooter(durabilityShort, discardedResultShort, durabilityMedium, discardedResultMedium);
  }

  /**
   * Displays session metrics including the total number of unhappy guests and the total cost of waste.
   *
   * @param totalUnhappyGuests The total number of unhappy guests.
   * @param totalWasteCost The total cost of wasted meals.
   */
  public void displaySessionMetrics(int totalUnhappyGuests, double totalWasteCost) {
    System.out.println("\nSession Metrics:");
    System.out.println("Total Unhappy Guests: " + totalUnhappyGuests);
    System.out.println("Total Waste Cost: $" + totalWasteCost);
  }
}
