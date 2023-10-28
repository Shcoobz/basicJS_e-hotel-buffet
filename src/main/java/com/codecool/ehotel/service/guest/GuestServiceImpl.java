package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;
import com.codecool.ehotel.model.GuestType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GuestServiceImpl implements GuestService {
  private int guestIndex = 0; // keeps track of index of next guest to generate
  private final Random random = new Random();

  @Override
  public Guest generateRandomGuest(LocalDate seasonStart, LocalDate seasonEnd) {

/*    List<String> guestNames = List.of("Alice",
        "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Henry",
        "Isabella", "Jack", "Kate", "Liam", "Mia", "Noah",
        "Olivia", "Paul", "Quinn", "Rachel", "Samuel", "Sophia",
        "Thomas", "Ursula", "Victor", "Willow", "Xavier",
        "Yvonne", "Zachary", "Abigail", "Benjamin", "Chloe");*/

    List<String> guestNames = List.of(
        "Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Henry",
        "Isabella", "Jack", "Kate", "Liam", "Mia", "Noah",
        "Olivia", "Paul", "Quinn", "Rachel", "Samuel", "Sophia",
        "Thomas", "Ursula", "Victor", "Willow", "Xavier",
        "Yvonne", "Zachary", "Abigail", "Benjamin", "Chloe",
        "Daniel", "Emily", "Finn", "Gemma", "Harry", "Ivy",
        "James", "Katherine", "Leo", "Megan", "Nathan", "Olivia",
        "Peter", "Quincy", "Rose", "Sam", "Tessa", "Ulysses",
        "Violet", "Walter", "Xena", "Yara", "Zane",
        "Alex", "Bella", "Caleb", "Daisy", "Ethan", "Fiona",
        "George", "Hannah", "Isaac", "Jessica", "Kai", "Lily",
        "Mason", "Nora", "Oscar", "Penelope", "Quincy", "Ruby",
        "Sofia", "Theodore", "Uma", "Vivian", "William", "Xander",
        "Yasmine", "Zara", "Arthur", "Beatrice", "Charles", "Diana",
        "Eleanor", "Freddie", "Giselle", "Henry", "Isabelle", "Jacob",
        "Kylie", "Liam", "Mila", "Nolan", "Olive", "Patrick", "Quinn",
        "Ronan", "Scarlett", "Tristan", "Ulysses", "Victoria", "Wesley",
        "Xander", "Yasmine", "Zara", "Ava", "Benjamin", "Chloe",
        "David", "Emma", "Finn", "Grace", "Hannah", "Isaac",
        "Jacob", "Katherine", "Lily", "Mason", "Nora", "Oliver",
        "Penelope", "Quincy", "Rose", "Sophia", "Thomas", "Ursula",
        "Victor", "William", "Xander", "Yara", "Zane");


    // Random random = new Random();

    List<GuestType> guestTypes = List.of(
        GuestType.BUSINESS,
        GuestType.TOURIST,
        GuestType.KID
    );

    if (guestIndex >= guestNames.size()) {
      guestIndex = 0; // Reset the index if we've used all guest names
    }

    String name = guestNames.get(random.nextInt(guestNames.size()));

    // GuestType[] guestTypes = GuestType.values();
    // GuestType guestType = guestTypes[random.nextInt(guestTypes.length)];

    // use guestIndex to cycle through guest types
    GuestType guestType = guestTypes.get(guestIndex % guestTypes.size());

    guestIndex++; // increment the index for the next guest

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
