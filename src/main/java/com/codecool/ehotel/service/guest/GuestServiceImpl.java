package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class GuestServiceImpl implements GuestService {

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
