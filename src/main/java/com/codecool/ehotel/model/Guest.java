package com.codecool.ehotel.model;

import java.time.LocalDate;

/**
 * Represents a guest in a hotel. This record encapsulates information about a guest,
 * including their name, type, check-in, and check-out dates.
 */
public record Guest(String name, GuestType guestType, LocalDate checkIn, LocalDate checkOut) {

  /**
   * Retrieves the name of the guest.
   *
   * @return The guest's name.
   */
  // The getter method for 'name' is implicitly provided by the record.

  /**
   * Retrieves the type of the guest (e.g., VIP, regular).
   *
   * @return The guest's type as a GuestType enumeration.
   */
  // The getter method for 'guestType' is implicitly provided by the record.

  /**
   * Retrieves the check-in date of the guest.
   *
   * @return The date the guest checked in.
   */
  // The getter method for 'checkIn' is implicitly provided by the record.

  /**
   * Retrieves the check-out date of the guest.
   *
   * @return The date the guest is scheduled to check out.
   */
  // The getter method for 'checkOut' is implicitly provided by the record.
}
