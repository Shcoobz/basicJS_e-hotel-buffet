package com.codecool.ehotel;

import com.codecool.ehotel.service.breakfast.BreakfastManager;

/**
 * Main entry point for the EHotel Buffet Application.
 * This class initializes and starts the breakfast service managed by the BreakfastManager.
 */
public class EHotelBuffetApplication {

  /**
   * Main method that starts the application.
   * It creates an instance of the BreakfastManager and invokes the serve method to begin the breakfast service.
   *
   * @param args Command line arguments (not used in this application).
   */
  public static void main(String[] args) {
    BreakfastManager breakfastManager = new BreakfastManager();

    breakfastManager.serve();
  }
}
