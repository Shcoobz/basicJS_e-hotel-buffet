package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
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
    setupInitialBuffet();
    // testing(buffetManager);
    // displayUpdatedBuffet(buffetManager);

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

  private static void testing(BreakfastManager breakfastManager) {
    // Consume something once
    buffetService.consumeFreshest(breakfastManager, MealType.SCRAMBLED_EGGS);
    System.out.println("\nBuffet after consumption:");
    display.showBreakfastMenu(breakfastManager);

    // Refill the buffet using the specification
    Map<MealType, Integer> refillSpec = BreakfastRefillSpecification.getSampleRefillSpecification();
    buffetService.refillBuffet(breakfastManager, refillSpec);
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
