package com.codecool.ehotel;

import com.codecool.ehotel.service.breakfast.BreakfastManager;

// TODO: buffet menu
//  - format list (names in center, dates & times right)
//  - add count to print out?

// TODO: general
//  - make names pretty (Scrambled eggs instead of SCRAMBLED_EGGS)

public class EHotelBuffetApplication {

  public static void main(String[] args) {

    // run breakfast
    BreakfastManager breakfastManager = new BreakfastManager();
    breakfastManager.serve();

  }

}
