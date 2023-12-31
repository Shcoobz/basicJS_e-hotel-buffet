package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Interface defining services related to guest management in a hotel.
 * This interface includes methods for generating random guests and retrieving guests for a specific day.
 */
public interface GuestService {

  /**
   * Generates a random guest with check-in and check-out dates falling within the specified season.
   * This method is used to simulate the creation of a guest with a random stay duration during a given season.
   *
   * @param seasonStart The start date of the season.
   * @param seasonEnd   The end date of the season.
   * @return A randomly generated Guest instance.
   */
  Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd);

  /**
   * Retrieves a set of guests who are staying at the hotel on a specific date.
   * This method filters the provided list of guests to find those whose stay includes the specified date.
   *
   * @param guests The list of all guests to filter from.
   * @param date   The date for which to retrieve the guests.
   * @return A set of Guests who are staying at the hotel on the specified date.
   */
  Set<Guest> getGuestsForDay(List<Guest> guests, LocalDate date);
}
