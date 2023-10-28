package com.codecool.ehotel;

import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.*;
import com.codecool.ehotel.ui.DisplayBreakfast;

import java.util.Map;

// TODO: make names pretty
// TODO: formatting of buffet list (names in center, dates & times right)

public class EHotelBuffetApplication {
  private static BuffetService buffetService = new BuffetServiceImpl();
  private static DisplayBreakfast display = new DisplayBreakfast();

  public static void main(String[] args) {

    BreakfastManager breakfastManager = setupInitialBuffet();
    // testing(buffetManager);
    // displayUpdatedBuffet(buffetManager);

    // Initialize services

    // Generate guests for the season

    // run breakfast

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
}
