package com.codecool.ehotel;

import com.codecool.ehotel.service.buffet.BuffetManager;
import com.codecool.ehotel.service.buffet.BuffetService;
import com.codecool.ehotel.service.buffet.BuffetServiceImpl;
import com.codecool.ehotel.ui.DisplayBreakfast;

public class EHotelBuffetApplication {

  public static void main(String[] args) {

    // Initialize services

    // initialize BreakfastBuffet
    BuffetService buffetService = new BuffetServiceImpl();
    BuffetManager buffetManager = buffetService.createSampleBuffet();

    // Generate guests for the season

    // display prepared breakfast menu
    DisplayBreakfast display = new DisplayBreakfast();
    display.showBreakfastMenu(buffetManager);

    // run breakfast

  }
}
