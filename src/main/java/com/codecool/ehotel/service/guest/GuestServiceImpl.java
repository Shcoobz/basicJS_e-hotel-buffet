package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Implementation of the GuestService interface.
 * Provides services related to guest management, including generating random guests and retrieving guests for a specific day.
 */
public class GuestServiceImpl implements GuestService {

  /**
   * Generates a random guest with a randomized name, guest type, and stay duration within a specified season.
   * The method selects a random name from a predefined list, a random guest type, and random check-in and check-out dates.
   *
   * @param seasonStart The start date of the season.
   * @param seasonEnd The end date of the season.
   * @return A randomly generated Guest instance.
   */
  @Override
  public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {

    List<String> guestNames = List.of("Alice",
        "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Henry",
        "Isabella", "Jack", "Kate", "Liam", "Mia", "Noah",
        "Olivia", "Paul", "Quinn", "Rachel", "Samuel", "Sophia",
        "Thomas", "Ursula", "Victor", "Willow", "Xavier",
        "Yvonne", "Zachary", "Abigail", "Benjamin", "Chloe");

    Random random = new Random();

    String name = guestNames.get(random.nextInt(guestNames.size()));

    GuestType[] guestTypes = GuestType.values();
    GuestType guestType = guestTypes[random.nextInt(guestTypes.length)];

    LocalDate checkIn = seasonStart.plusDays(random.nextInt((int) ChronoUnit.DAYS.between(seasonStart, seasonEnd)));
    LocalDate checkOut = checkIn.plusDays(random.nextInt(7));
    if (checkOut.isAfter(seasonEnd)) {
      checkOut = seasonEnd;
    }
    if (checkIn == checkOut) {
      checkOut = checkOut.plusDays(1);
    }

    return new Guest(name, guestType, checkIn, checkOut);
  }

  /**
   * Retrieves a set of guests who are staying at the hotel on a specified date.
   * This method filters the provided list of guests to include only those whose stay includes the given date.
   *
   * @param guests The list of all guests to filter from.
   * @param date The date for which to retrieve the guests.
   * @return A set of Guests who are staying at the hotel on the specified date.
   */
  @Override
  public Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date) {
    Set<Guest> guestsForToday = new HashSet<>();
    for (Guest guest : guests) {
      if (!date.isBefore(guest.checkIn()) && !date.isAfter(guest.checkOut())) {
        guestsForToday.add(guest);
      }
    }
    return guestsForToday;
  }
}
