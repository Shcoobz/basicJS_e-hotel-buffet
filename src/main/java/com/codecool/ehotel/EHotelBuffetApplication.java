package com.codecool.ehotel;

import com.codecool.ehotel.model.MealType;
import com.codecool.ehotel.service.buffet.BuffetManager;
import com.codecool.ehotel.service.buffet.BuffetRefillSpecification;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.ui.DisplayBreakfast;

import java.util.Map;

public class EHotelBuffetApplication {

  public static void main(String[] args) {

    // Initialize services

    // initialize BreakfastBuffet
    BuffetService buffetService = new BuffetServiceImpl();

    // Create initial buffet
    BuffetManager buffetManager = buffetService.createSampleBuffet();

    // Display initial buffet
    DisplayBreakfast display = new DisplayBreakfast();
    System.out.println("\nInitial Breakfast Buffet:");
    display.showBreakfastMenu(buffetManager);

    // Refill the buffet using the specification
    Map<MealType, Integer> refillSpec = BuffetRefillSpecification.getSampleRefillSpecification();
    buffetService.refillBuffet(buffetManager, refillSpec);

    // Generate guests for the season

    // Display the updated buffet
    System.out.println("\nUpdated Breakfast Buffet:");
    display.showBreakfastMenu(buffetManager);


    // run breakfast

  }
}
