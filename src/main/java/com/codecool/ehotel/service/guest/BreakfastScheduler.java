package com.codecool.ehotel.service.guest;

import com.codecool.ehotel.model.Guest;

import java.time.LocalTime;
import java.util.*;

/**
 * Manages the scheduling of breakfast for hotel guests.
 * This class is responsible for dividing guests into different breakfast cycles based on a predefined schedule.
 */
public class BreakfastScheduler {

  /**
   * Splits the list of guests into multiple breakfast cycles.
   * Each cycle represents a distinct time slot in which a group of guests can have breakfast.
   * The scheduling is randomized within the operational hours of the breakfast service.
   *
   * @param guests The list of guests to be scheduled for breakfast.
   * @return A list of sets, each set representing a group of guests assigned to a specific breakfast cycle.
   */
  public List<Set<Guest>> splitGuestsIntoBreakfastCycles(List<Guest> guests) {
    int numberOfCycles = 8;
    List<Set<Guest>> breakfastCycles = new ArrayList<>(numberOfCycles);
    for (int i = 0; i < numberOfCycles; i++) {
      breakfastCycles.add(new HashSet<>());
    }

    Random random = new Random();
    LocalTime startTime = LocalTime.of(6, 0);
    LocalTime endTime = LocalTime.of(10, 0);

    for (Guest guest : guests) {
      int cycleIndex = random.nextInt(numberOfCycles);
      LocalTime randomTime = startTime.plusMinutes((cycleIndex * 30));

      if (randomTime.isBefore(endTime)) {
        breakfastCycles.get(cycleIndex).add(guest);
      }
    }

    return breakfastCycles;
  }
}



