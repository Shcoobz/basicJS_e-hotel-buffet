package com.codecool.ehotel;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.service.breakfast.BreakfastManager;
import com.codecool.ehotel.service.guest.GuestService;
import com.codecool.ehotel.service.guest.GuestServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// TODO: make names pretty
// TODO: formatting of buffet list (names in center, dates & times right)

public class EHotelBuffetApplication {

  public static void main(String[] args) {

    // Initialize services
    GuestService guestService = new GuestServiceImpl();

    // Define the "season" date limits
    LocalDate seasonStart = LocalDate.of(2023, 11, 1);
    LocalDate seasonEnd = LocalDate.of(2024, 11, 5);
    LocalDate currentDate = seasonStart;

    // Generate guests for the season
    List<Guest> guests = generateGuestsForSeason(guestService, seasonStart, seasonEnd);

    while (!currentDate.isAfter(seasonEnd)) {
      Set<Guest> guestsForToday = guestService.getGuestsForDay(guests, currentDate);
      currentDate = currentDate.plusDays(1);
    }
    System.out.println(guests);

    // run breakfast
    BreakfastManager breakfastManager = new BreakfastManager();
    breakfastManager.serve();

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
