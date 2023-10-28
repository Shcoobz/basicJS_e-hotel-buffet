package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.MealDurability;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.*;
import com.codecool.ehotel.ui.DisplayBreakfast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;

// TODO: make names pretty
// TODO: formatting of buffet list (names in center, dates & times right)

public class EHotelBuffetApplication {
  private static BuffetService buffetService = new BuffetServiceImpl();
  private static DisplayBreakfast display = new DisplayBreakfast();

  public static void main(String[] args) {

    // Initialize services
    GuestService guestService = new GuestServiceImpl();

    // Define the "season" date limits
    LocalDate seasonStart = LocalDate.of(2023, 11, 1);
    LocalDate seasonEnd = LocalDate.of(2024, 11, 5);
    LocalDate currentDate = seasonStart;

    // Generate guests for the season
    List<Guest> guests = generateGuestsForSeason(guestService, seasonStart, seasonEnd);

    // run breakfast
    BreakfastManager breakfastManager = setupInitialBuffet();

    // testing
    testingConsumeMeal(breakfastManager);
    testingRefillBuffet(breakfastManager);
    displayUpdatedBuffet(breakfastManager);

    // Simulating older meals for testing purposes
    simulateOldMeals(breakfastManager);

    // Simulating waste collection
    simulateWasteCollection(breakfastManager);

    displayUpdatedBuffet(breakfastManager);


    while (!currentDate.isAfter(seasonEnd)) {
      Set<Guest> guestsForToday = guestService.getGuestsForDay(guests, currentDate);
      currentDate = currentDate.plusDays(1);
    }
    System.out.println(guests);

  }

  private static BreakfastManager setupInitialBuffet() {
    BreakfastManager breakfastManager = buffetService.createSampleBuffet();
    // System.out.println("\nInitial Breakfast Buffet:");
    display.showBreakfastMenu(breakfastManager);

    return breakfastManager;
  }

  private static void testingConsumeMeal(BreakfastManager breakfastManager) {
    buffetService.consumeFreshest(breakfastManager, MealType.SCRAMBLED_EGGS);
    System.out.println("\nBuffet after consumption:");
    display.showBreakfastMenu(breakfastManager);
  }

  private static void testingRefillBuffet(BreakfastManager breakfastManager) {
    Map<MealType, Integer> refillSpec = BreakfastRefillSpecification.getSampleRefillSpecification();
    buffetService.refillBuffet(breakfastManager, refillSpec);
  }

  private static void simulateOldMeals(BreakfastManager breakfastManager) {
    List<MealPortion> allMeals = breakfastManager.getAllMeals();

    for (int i = 0; i < allMeals.size() / 2; i++) { // make half of the meals a day older
      allMeals.get(i).setTimestamp(allMeals.get(i).getTimestamp().minusDays(1));
    }
  }

  private static void simulateWasteCollection(BreakfastManager breakfastManager) {
    // Simulating scenario to remove all SHORT durability meals that were added before today's date
    int wastedCost = buffetService.collectWaste(breakfastManager, MealDurability.SHORT, LocalDate.now().atStartOfDay());
    System.out.println("\nCost of wasted meals: $" + wastedCost);
  }

  private static void displayUpdatedBuffet(BreakfastManager breakfastManager) {
    System.out.println("\nUpdated Breakfast Buffet:");
    display.showBreakfastMenu(breakfastManager);
  }

  private static List<Guest> generateGuestsForSeason(GuestService guestService, LocalDate seasonStart, LocalDate seasonEnd) {
    List<Guest> generatedGuests = new ArrayList<>();
    int numberOfGuestsToGenerate = 30;
    for (int i = 0; i < numberOfGuestsToGenerate; ++i) {
      Guest guest = guestService.generateRandomGuest(seasonStart, seasonEnd);
      generatedGuests.add(guest);
    }
    return generatedGuests;
  }

}
